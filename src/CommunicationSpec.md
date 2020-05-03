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
- [enter_random](#enter_random)
- [ready_start](#ready_start)
- [play_card](#play_card)
- move
- end_round
- surrender

## message_id 

### enter_room
Send when client wants to join specific room.

```json
{
    "msg": "enter_room",
    "data": {
        "room_number": integer
    }
}
```
<table>
<tr>
<th colspan="2">Fields</th>
</tr>

<tr>
<td>room_number</td>
<td>Room number of the room the client requires to join</td>
</tr>
</table>

### enter_random
Send when client wants to join a random room.

**No data required**

```json
{
    "msg": "enter_random",
    "data": {}
}
```

### ready_start
Prerequest: client is in a room

Send when client is ready for a game

**No data required**

```json
{
    "msg": "ready_start",
    "data": {}
}
```

### play_card
Rrerequest: game has started

Send when client want to play a card

```json
{
    "msg": "play_card",
    "data": {
        "card_id": integer
    }
}
```

<table>
<tr>
<th colspan="2">Fields</th>
</tr>

<tr>
<td>card_id</td>
<td>The id of the card the client want to play. For more information about card id, see <a href="#Card_id">Card_id</a> section.</td>
</tr>
</table>
