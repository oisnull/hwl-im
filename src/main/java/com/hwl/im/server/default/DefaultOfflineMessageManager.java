package com.hwl.im.server.default;

public class DefaultOfflineMessageManager implements IOfflineMessageStorage
{
    static Logger log = LogManager.getLogger(DefaultOfflineMessageManager.class.getName());

    protected ConcurrentHashMap<Long, Queue<ImMessageContext>> messageContainer ;
    private String _sourceType;

    public DefaultOfflineMessageManager(String sourceType)
    {
        messageContainer = = new ConcurrentHashMap<>();
        _sourceType = sourceType ;//"temp" : "offline";
    }

    public void addFirst(long userid, ImMessageContext messageContext)
    {
        if (!messageContainer.ContainsKey(userid) || messageContainer[userid] == null)
        {
            messageContainer[userid] = new List<ImMessageContext>();
        }
        messageContainer[userid].Insert(0, messageContext);

        if (messageContainer[userid].Count > 0)
        {
            log.debug("addFirst : Userid({}) {} message count is {}", userid, _sourceType, messageContainer[userid].Count);
        }
    }

    public void addMessage(long userid, ImMessageContext messageContext)
    {
        if (!messageContainer.ContainsKey(userid) || messageContainer[userid] == null)
        {
            messageContainer[userid] = new List<ImMessageContext>();
        }
        messageContainer[userid].Add(messageContext);

        if (messageContainer[userid].Count > 0)
        {
            log.debug("addMessage : Userid({}) {} message count is {}", userid, _sourceType, messageContainer[userid].Count);
        }
    }

    public void addMessages(long userid, List<ImMessageContext> messageContexts)
    {
        if (!messageContainer.ContainsKey(userid) || messageContainer[userid] == null)
        {
            messageContainer[userid] = new List<ImMessageContext>();
        }
        messageContainer[userid].AddRange(messageContexts);

        if (messageContainer[userid].Count > 0)
        {
            log.debug("addMessages : Userid({}) {} message count is {}", userid, _sourceType, messageContainer[userid].Count);
        }
    }

    public List<ImMessageContext> getMessages(long userid)
    {
        return messageContainer[userid];
    }

    public ImMessageContext pollMessage(long userid)
    {
        if (messageContainer.ContainsKey(userid) && messageContainer[userid] != null && messageContainer[userid].Count > 0)
        {
            ImMessageContext firstModel = messageContainer[userid][0];
            messageContainer[userid].RemoveAt(0);
            return firstModel;
        }
        else
        {
            return null;
        }
    }
}