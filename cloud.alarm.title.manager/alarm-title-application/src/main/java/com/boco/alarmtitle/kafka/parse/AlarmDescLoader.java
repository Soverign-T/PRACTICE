package com.boco.alarmtitle.kafka.parse;

import com.boco.gutil.registry.client.util.ConfigurationHelper;
import com.boco.ucmp.client.Configuration;
import com.boco.xdpp.model.alarm.dynamic.DynamicMessageConstant;
import com.boco.xdpp.model.alarm.dynamic.impl.MetaManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AlarmDescLoader {

    private static Logger logger = Logger.getLogger(AlarmDescLoader.class);

    /**
     * ��ʼ��alarm.proto�İ汾������
     */
    public static void loadAlarmObjectMapping() {
        byte[] metadata = null;
        try {
            metadata = readFileToBytes();
        }catch (Exception e){
            logger.error("read pb desc err..." , e);
        }
        if (metadata == null) {
            logger.error("configuration changed,data is null");
            return;
        }
        int dataLength = -1;
        try {

            dataLength = metadata.length;
            if (dataLength == 0) {
                logger.error("alarmObjectDesc changed,data.length is 0.path=" + ",data.length=" + dataLength);
                return;
            }
            logger.info("alarmObjectDesc changed,begin .path=" + ",data.length=" + dataLength);

            Map<String, byte[]> map = new HashMap<String, byte[]>();
            map.put("0", metadata);
            MetaManager.getInstance().setMap(map);
            MetaManager.getInstance().init();
            DynamicMessageConstant.factory.buildMeta(DynamicMessageConstant.LATEST_META, metadata);
            logger.info("alarmObjectDesc changed,end .path=" + ",data.length=" + dataLength);
        } catch (Exception e) {
            logger.error("alarmObjectDesc changed,end .path=" + ",data.length=" + dataLength, e);
        }
    }

    public static byte[] readFileToBytes() throws IOException {
        Configuration configuration = ConfigurationHelper.getUcmpConf();
        byte[] bytes = configuration.getBytes("/public/model/alarm.proto/desc");
        return bytes;
        /*InputStream in = new FileInputStream(path);
        BufferedInputStream bufin = new BufferedInputStream(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);

        // System.out.println("Available bytes:" + in.available());

        byte[] temp = new byte[1024];
        int size = 0;
        while ((size = bufin.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        bufin.close();

        byte[] content = out.toByteArray();
        return content;*/
    }
}
