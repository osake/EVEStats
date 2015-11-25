package com.tlabs.eve.api.character;



import com.tlabs.eve.api.EveAPIParser;
import com.tlabs.eve.parser.SetAttributePropertyRule;
import com.tlabs.eve.parser.SetNextRule;

import org.apache.commons.digester.Digester;

public final class PlanetaryColoniesParser extends EveAPIParser<PlanetaryColoniesResponse> {

    public PlanetaryColoniesParser() {
        super(PlanetaryColoniesResponse.class);
    }

    protected void onInit(Digester digester) {
        digester.addObjectCreate("eveapi/result/rowset/row", PlanetaryColony.class);
        digester.addRule("eveapi/result/rowset/row", new SetAttributePropertyRule());
        digester.addRule("eveapi/result/rowset/row", new SetNextRule("addColony"));
    }
}
