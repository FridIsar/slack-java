package io.slack.network.handlerMessages;

import io.slack.network.handlerMessages.typeMessagesHandler.channels.*;
import io.slack.network.handlerMessages.typeMessagesHandler.user.*;

import java.util.HashMap;
import java.util.Map;

public class ClientMessageMapping {
    // à transférer dans ClientMessageType
    private static final Map<Integer, ClientMessageHandler> handlers =
            new HashMap<Integer, ClientMessageHandler>() {
        {
            put(ClientMessageType.SIGNIN.getValue(), new SignInMessage());
            put(ClientMessageType.SIGNUP.getValue(), new SignUpMessage());
            put(ClientMessageType.DELETEUSER.getValue(), new DeleteUserMessage());
            put(ClientMessageType.DISCONNECT.getValue(), new DisconnectMessage());

            put(ClientMessageType.UPDATEUSER.getValue(), new UpdateUserMessage());

            put(ClientMessageType.CREATECHANNEL.getValue(), new CreateChannelMessage());
            put(ClientMessageType.DELETECHANNEL.getValue(), new DeleteChannelMessage());
            put(ClientMessageType.ADDUSERCHANNEL.getValue(), new AddUserChannelMessage());
            put(ClientMessageType.GETUSERSCHANNEL.getValue(), new GetUsersChannelMessage());
            put(ClientMessageType.GETCHANNELSUSER.getValue(), new GetChannelsUserMessage());
            put(ClientMessageType.DELETEUSERCHANNEL.getValue(), new DeleteUserChannelMessage());

            put(ClientMessageType.GETCHANNELS.getValue(), new GetChannelsMessage());

            put(ClientMessageType.ADDPOSTCHANNEL.getValue(), new AddPostChannelMessage());
            put(ClientMessageType.DELETEPOSTCHANNEL.getValue(), new DeletePostChannelMessage());
            put(ClientMessageType.GETPOSTSCHANNEL.getValue(), new GetPostsChannelMessage());

            put(ClientMessageType.ADDFRIEND.getValue(), new AddFriendshipMessage());
            put(ClientMessageType.DELETEFRIEND.getValue(), new DeleteFriendshipMessage());
            put(ClientMessageType.GETFRIENDS.getValue(), new GetFriendsMessage());

            put(ClientMessageType.ADDPOSTFRIEND.getValue(), new AddPostFriendMessage());
            put(ClientMessageType.GETPOSTSFRIEND.getValue(), new GetPostsFriendMessage());

        }
    };

    public static Map<Integer, ClientMessageHandler> getMapping() {
        return new HashMap<>(handlers);
    }
}
