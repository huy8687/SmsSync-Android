package hero.huy.smssync;

import android.test.InstrumentationTestCase;

import hero.huy.smssync.model.CommandMessage;
import hero.huy.smssync.model.CommandType;

/**
 * Created by huybu on 9/6/2015.
 */
public class CommandMessageTest extends InstrumentationTestCase {

    private CommandMessage cmd;
    private CommandMessage cmd2;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        cmd = new CommandMessage(CommandType.IN_COMING_SMS,"123456789","HELO","192.168.56.1");
        cmd2 = new CommandMessage("1         123456789           HELO");
    }

    public void testContructor(){
        String test = cmd.toString();
        assertEquals(test,"1         123456789           HELO");
    }

    public void testContructorRaw(){
        assertEquals(cmd2.getType(),"1");
        assertEquals(cmd2.getPhoneNumber(),"123456789");
        assertEquals(cmd2.getContent(),"HELO");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
