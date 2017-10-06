package com.acme.edu.message;
import com.acme.edu.Saver;
import com.acme.edu.formatter.Formatter;

public class IntMessage extends Message {
    private Saver saver = new Saver();
    public IntMessage(StringBuilder content, Formatter formatter) {
        super(content, formatter);
    }

    @Override
    public void formatContent(StringBuilder prevContent) throws IllegalArgumentException {
        StringBuilder temp = new StringBuilder("");
        int prevVal;
        int currentVal;
        try {
            if(prevContent.length() == 0) {
                prevVal = 0;
            } else {
                prevVal = Integer.parseInt(prevContent.toString());
            }
            currentVal = Integer.parseInt(getContent().toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("int message illegal argument", e);
        }


        if(Integer.MAX_VALUE - (prevVal + currentVal) < 0) {
            if(prevVal > currentVal ) {
                temp.append(Integer.MAX_VALUE);
                prevVal = - (Integer.MAX_VALUE - (prevVal + currentVal));
            } else {
                temp.append(prevVal);
                prevVal = currentVal;
            }

            setContent(temp);
            concatenateWithPrefix();

            try {
                saver.save(getContent());
            } catch(Exception e) {
                throw new IllegalArgumentException("logger controller exception", e);
            }

            temp.setLength(0);
            temp.append(prevVal);
        } else {
            temp.append((prevVal + currentVal));
        }

        setContent(temp);
    }

    @Override
    public boolean isSameType(Message prevMessage) {
        if(prevMessage == null) {
            return false;
        }
        return prevMessage instanceof IntMessage;
    }

    @Override
    public void concatenateWithPrefix() {
        getFormatter().formatMessage(getContent());
    }

}
