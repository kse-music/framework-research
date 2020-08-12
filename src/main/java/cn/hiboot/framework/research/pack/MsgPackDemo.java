package cn.hiboot.framework.research.pack;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.List;

/**
 * description about this class
 *
 * @author DingHao
 * @since 2019/8/15 22:50
 */
public class MsgPackDemo {

    @Test
    public void demo() throws IOException {
        List<String> src = Lists.newArrayList("msgpack","kumofs","viver");
        MessagePack msgpack = new MessagePack();

        byte[] raw = msgpack.write(src);

        List<String> dst = msgpack.read(raw, Templates.tList(Templates.TString));

        System.out.println(dst);

    }

}
