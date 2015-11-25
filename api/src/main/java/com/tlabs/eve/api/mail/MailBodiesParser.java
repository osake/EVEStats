package com.tlabs.eve.api.mail;



import com.tlabs.eve.api.EveAPIParser;
import com.tlabs.eve.parser.SetAttributePropertyRule;
import com.tlabs.eve.parser.SetNextRule;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;

public class MailBodiesParser extends EveAPIParser<MailBodiesResponse> {

    public MailBodiesParser() {
        super(MailBodiesResponse.class);
    }

    @Override
    protected void onInit(Digester digester) {
        digester.addObjectCreate("eveapi/result/rowset/row", MailMessage.class);
        digester.addRule("eveapi/result/rowset/row", new SetNextRule("addMessage"));
        digester.addRule("eveapi/result/rowset/row", new SetAttributePropertyRule() {

            @Override
            public void doBody(String name, String text) {
                super.doBody(name, text);
                MailMessage m = (MailMessage) digester.peek();
                m.setBody(clean(text));
            }
        });
    }

    private static String clean(final String body) {
        return StringUtils.isBlank(body) ? "" : body.trim();
    }
}
