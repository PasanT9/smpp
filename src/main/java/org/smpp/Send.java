package org.smpp;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.jsmpp.bean.*;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.session.SubmitSmResult;
import org.jsmpp.util.RelativeTimeFormatter;
import org.jsmpp.util.TimeFormatter;

public class Send {
    public static void main(String[] args) throws Exception {
        SMPPSession session = new SMPPSession();
        session.connectAndBind("localhost", 8056,
                               new BindParameter(BindType.BIND_TX, "j", "jpwd", "cp", TypeOfNumber.UNKNOWN,
                                                 NumberingPlanIndicator.UNKNOWN, null));

        String message =
                "REF:4dgg Confirmé. fcbhdgjhj dgmjkykyiktyury  a été retiré de  sdb sdhjkd.Le solde est de  USD 10. Renseignements au 083290222";

        TimeFormatter timeFormatter = new RelativeTimeFormatter();

        DataCoding dataCoding = new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1, false);

        byte[] data = message.getBytes(StandardCharsets.UTF_8);
        System.out.println(message);

        SubmitSmResult submitSmResult = session.submitShortMessage("CMT", TypeOfNumber.INTERNATIONAL,
                                                                   NumberingPlanIndicator.UNKNOWN, "1616",
                                                                   TypeOfNumber.INTERNATIONAL,
                                                                   NumberingPlanIndicator.UNKNOWN, "628176504657",
                                                                   new ESMClass(), (byte) 0, (byte) 1,
                                                                   timeFormatter.format(new Date(
                                                                           System.currentTimeMillis() + 60000)), null,
                                                                   new RegisteredDelivery(SMSCDeliveryReceipt.DEFAULT),
                                                                   (byte) 0, dataCoding, (byte) 0, data);

        session.unbindAndClose();
    }
}
