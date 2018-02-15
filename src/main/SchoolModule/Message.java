package main.SchoolModule;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Feng Jiang on 2017/10/3.
 */

// Message 采用sql存储

public class Message {
    private UUID _id=UUID.randomUUID();
    private UUID AssignmentId;
    private UUID StudentId;
    private UUID TeacherId;  //2者必备一个。
    private String MessageBody;
    private Date MessageDateTime;


}
