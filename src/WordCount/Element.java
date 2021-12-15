package WordCount;

import java.util.Date;

/*
    Alejandro Lopez
    Operating Systems Project 3
    Nova Southeastern University
 */

//An element is the same as a node
//The methods are set to be used inside the cache class
public class Element {
    private String data_content;
    private Date timestamp;

    public String getValue() {
        return data_content;
    }

    public void setValue(String value) {
        this.data_content = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean equals(Element e) {
        return data_content.equals(e.getValue());
    }
}
