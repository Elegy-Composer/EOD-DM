# Websocket communication specification
This spec specifies the messages' formats used in the communication between the game server and clients and gives examples of messages.

## Message format
Every message sent to the server should follow the format below, otherwise, the server could ignore it.

```json
{
    "msg": message_id,
    "data": {
        data required by message_id
    }
}
```

The available message_id are shown below:
- [enter_room](#enter_room)
- enter_random
- ready_start
- play_card
- move
- end_round
- surrender

## message_id 

### enter_room

```json
{
    "msg": "enter_room",
    "data": {
        "player_id": integer,
        "room_number": integer
    }
}
```
