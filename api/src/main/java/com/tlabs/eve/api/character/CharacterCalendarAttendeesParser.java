package com.tlabs.eve.api.character;

import com.tlabs.eve.api.EveAPIParser;
import com.tlabs.eve.parser.SetAttributePropertyRule;
import com.tlabs.eve.parser.SetNextRule;

import org.apache.commons.digester.Digester;

public final class CharacterCalendarAttendeesParser extends EveAPIParser<CharacterCalendarAttendeesResponse> {

    public CharacterCalendarAttendeesParser() {
        super(CharacterCalendarAttendeesResponse.class);
    }

    @Override
    protected void onInit(Digester digester) {
        digester.addObjectCreate("eveapi/result/rowset/row", CharacterCalendar.Attendee.class);
        digester.addRule("eveapi/result/rowset/row", new SetAttributePropertyRule());
        digester.addRule("eveapi/result/rowset/row", new SetNextRule("addAttendee"));
    }
}
