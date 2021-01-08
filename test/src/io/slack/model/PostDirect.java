package io.slack.model;


//todo :
// /creer une table de post direct contenant un id / un message / deux users_id / une sending_date / une modification_date / un boolean with attachment
// /classe Service : PostDirectService  /

//

public class PostDirect extends Post {

    public PostDirect(){}

    public PostDirect(User author, String message, ChannelDirect channelDirect) {
        super(author, message, channelDirect);
    }
}
