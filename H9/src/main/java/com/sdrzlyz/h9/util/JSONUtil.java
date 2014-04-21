package com.sdrzlyz.h9.util;


import com.sdrzlyz.h9.entity.MessagesInfo;
import com.sdrzlyz.h9.exception.POAException;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by sdrzlyz on 14-3-26.
 */
public class JSONUtil {
    private static JsonGenerator jsonGenerator;
    private static ObjectMapper objectMapper = new ObjectMapper();


    public static void close() {
        if (objectMapper != null)
            objectMapper = null;
        if (jsonGenerator != null)
            jsonGenerator = null;
    }

    public static String format(Object obj) throws POAException {
        if (objectMapper == null)
            objectMapper = new ObjectMapper();
        String s;
        try {
            s = objectMapper.writeValueAsString(obj);
        } catch (JsonGenerationException jsongenerationexception) {
            throw new POAException(32);
        } catch (JsonMappingException jsonmappingexception) {
            throw new POAException(32);
        } catch (IOException ioexception) {
            throw new POAException(32);
        }
        return s;
    }

    public static MessagesInfo parse(String s, Class class1) throws POAException {
        if (objectMapper == null)
            objectMapper = new ObjectMapper();
        MessagesInfo messagesinfo;
        try {
            if (jsonGenerator == null)
                jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
            messagesinfo = (MessagesInfo) objectMapper.readValue(s, class1);
            if (messagesinfo.getSuccess() != 1)
                throw new POAException(messagesinfo.getSuccess(), messagesinfo.getMes());
        } catch (JsonParseException jsonparseexception) {
            jsonparseexception.printStackTrace();
            throw new POAException(25);
        } catch (JsonMappingException jsonmappingexception) {
            jsonmappingexception.printStackTrace();
            throw new POAException(25);
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
            throw new POAException(25);
        }
        return messagesinfo;
    }

}
