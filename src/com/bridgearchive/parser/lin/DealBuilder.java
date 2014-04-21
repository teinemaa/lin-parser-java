package com.bridgearchive.parser.lin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bridgearchive.bridgedata.Bidding;
import com.bridgearchive.bridgedata.Board;
import com.bridgearchive.bridgedata.Call;
import com.bridgearchive.bridgedata.Card;
import com.bridgearchive.bridgedata.Deal;
import com.bridgearchive.bridgedata.Play;
import com.bridgearchive.parser.lin.exception.ParseException;
import com.bridgearchive.parser.lin.tag.MbTag;
import com.bridgearchive.parser.lin.tag.McTag;
import com.bridgearchive.parser.lin.tag.MdTag;
import com.bridgearchive.parser.lin.tag.PcTag;
import com.bridgearchive.parser.lin.tag.QxTag;
import com.bridgearchive.parser.lin.tag.Tag;

public class DealBuilder {

	private TagReader tags;
	private int ignoredDeals = 0;
	private int linNr;

	public DealBuilder(List<Tag> tags, int linNr) {
		this.linNr = linNr;
		this.tags = new TagReader(tags);
	}

	public Collection<Deal> buildDeals() throws ParseException {

		Collection<Deal> toReturn = new ArrayList<Deal>();

		Map<Integer, Play> otherRoom = new HashMap<Integer, Play>();

		QxTag qx = tags.nextBoard();
		while (qx != null) {

			int nr = qx.getBoardNr();
			Board board;

			if (otherRoom.containsKey(nr)) {
				board = otherRoom.get(nr).getBidding().getBoard();
			} else {
				MdTag md = tags.nextTag(MdTag.class);
				if (md == null) {
					qx = tags.nextBoard();
					continue;
				}
				board = new Board(md.getHands(), nr);
			}

			List<Call> calls = new ArrayList<Call>();
			MbTag mb = tags.nextTag(MbTag.class);
			while (mb != null) {
				calls.add(mb.getCall());
				mb = tags.nextTag(MbTag.class);
			}
			if (calls.size() < 4) {
				qx = tags.nextBoard();
				continue;
				//throw new ParseException("build deal too few bids", "board " + nr + " bids " + calls.size());
			}
			Bidding bidding = new Bidding(board, calls, qx.isOpenRoom());

			List<Card> cardPlays = new ArrayList<Card>();
			PcTag pc = tags.nextTag(PcTag.class);
			while (pc != null) {
				cardPlays.add(pc.getCard());
				pc = tags.nextTag(PcTag.class);
			}
			McTag mc = tags.nextTag(McTag.class);
			Integer claim = mc == null ? null : mc.getClaimedTrickCount();

			if (claim == null && cardPlays.size() != 52) {
				qx = tags.nextBoard();
				continue;
				//throw new ParseException("unknown result", "board " + nr + " no claim and only " + cardPlays.size() + " cards played");
			}
			Play play = new Play(bidding, cardPlays, claim);

			if (otherRoom.containsKey(nr)) {
				Play open;
				Play closed;
				if (play.getBidding().isOpenRoom()) {
					open = play;
					closed = otherRoom.get(nr);
				} else {

					open = otherRoom.get(nr);
					closed = play;
				}
				if (open.getBidding().isOpenRoom() == closed.getBidding().isOpenRoom()) {
					throw new ParseException("duplicate result", "multiple results from same room");
				}
				toReturn.addAll(Deal.createDeals(open, closed, linNr));
				otherRoom.remove(nr);
			} else {
				otherRoom.put(nr, play);
			}

			qx = tags.nextBoard();
		}
		ignoredDeals += otherRoom.size();
		return toReturn;
	}

	public int getIgnoredDeals() {
		return ignoredDeals;
	}
}
