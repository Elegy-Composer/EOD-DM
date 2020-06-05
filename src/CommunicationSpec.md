# Websocket communication specification
This spec specifies the messages' formats used in the communication between the game server and clients and gives examples of messages.

# Table of contents
- [Custom Type](#custom-type)
    - [Player](#player)
    - [Card](#card)
    - [Object](#object)
    - [Effect](#effect)
    - [Position](#position)
- [Client](#client)
    - [client_message_id](#client_message_id)
- [Server](#server)
    - [server_message_id](#server_message_id)

# Custom Type
Game object data structure.

## Player
```jsonc
{
    "name": string,
    "role": string,
    "hand": integer,
    "deck": integer,
    "left_cost": integer,
    "max_cost": integer,
    "hand_cards": [card] // optional
}
```

| Field | Type | Description |
| --- | --- | --- |
| name | string | Player name. |
| role | string | Player role. <br> Value: `player` or `enemy`. |
| hand | integer | The number of cards in player's hand. |
| deck | integer | The number of cards in player's deck. |
| left_cost | integer | Player's left cost. |
| max_cost | integer | Player's max cost. |
| hand_cards (Optional) | [card](#card)[] | Cards in player's hand. <br>This field only appears if player role is `player`. |

## Card
```jsonc
{
    "id": integer,
    "name": string,
    "type": string,
    "cost": integer,
    "color": string,
    "description": string,
    "effects": [effect]
}
```

| Field | Type | Description |
| --- | --- | --- |
| id | integer | Card ID. |
| name | string | Card name. |
| type | string | Card type. |
| cost | integer | Cost of the card. |
| color | string | Color of the card. |
| description | string | Card description. |
| effects | [effect](#effect)[] | The effects attached to card. |

## Object
```jsonc
{
    "position": position,
    "owner_role": string,
    "color": string,
    "hp": integer,
    "atk": integer,
    "attack_range": [position],
    "effects": [effect]
}
```

| Field | Type | Description |
| --- | --- | --- |
| position | [position](#position) | Position of the object. |
| owner_role | string | Owner of the object. <br>Value: `player` or `enemy`. |
| color | string | Color of the object. |
| hp | integer | Object HP. |
| atk | integer | Object attack point. |
| attack_range | [position](#position)[] | Allowed position to attack. |
| effects | [effect](#effect)[] | The effects attached to object. |

## Effect
```jsonc
{
    "type": string,
    "description": description
}
```

| Field | Type | Description |
| --- | --- | --- |
| type | string | effect type |
| description | string | effect description |

## Position
```jsonc
[x, y]
```

| Field | Type | Description |
| --- | --- | --- |
| x | integer | x position |
| y | integer | y position |


# Client
Every message that client sends to the server should comply with the format below; otherwise, the server would ignore it.

```jsonc
{
    "id": message_id,
    "data": {
        // data required by message_id
    }
}
```

## client_message_id
- [enter_room](#enter_room)
- [ready_start](#ready_start)
- [choose_initial](#choose_initial)
- [play_card](#play_card)
- [move](#move)
- [attack](#attack)
- [end_round](#end_round)
- [surrender](#surrender)

### enter_room
Send when client wants to join a specific room.

```jsonc
{
    "id": "enter_room",
    "data": {
        "room_number": 0
    }
}
```

| Field | Type | Description |
| --- | --- | --- |
| room_number | integer | Room number that the client want to join. <br>0 stands for random room. |


### ready_start
Prerequisite: client is in a room

Send when client is ready for a game.

```jsonc
{
    "id": "ready_start",
    "data": {
        "deck_id": 1
    }
}
```

| Field | Type | Description |
| --- | --- | --- |
| deck_id | integer | The deck id that the client wants to use. |


### choose_initial
Send cards that player wants to keep.

```jsonc
{
    "id": "play_card",
    "data": {
        "cards": [1, 2]
    }
}
```

| Field | Type | Description |
| --- | --- | --- |
| cards | integer | Serveral card IDs that the client wants to keep. |

### play_card
Prerequisite: game has started and the player is qualified for doing actions.

Send when client wants to use a card.

```jsonc
{
    "id": "play_card",
    "data": {
        "card_id": 1,
        "position": position // optional
    }
}
```

| Field | Type | Description |
| --- | --- | --- |
| card_id | integer | The card id that the client wants to use. For more information about card id, see [Card_id](#Card_id) section. |
| position (Optional) | [position](#position) | The position of the card target. |

### move
Prerequisite: game has started and the player is qualified for doing action.

Send when client move an object.

```jsonc
{
    "id": "move",
    "data": {
        "original_position": [x1, y1],
        "target_position": [x2, y2]
    }
}
```

| Field | Type | Description |
| --- | --- | --- |
| original_position | [position](#position) | The original position of the object that the player wants to move. |
| target_position | [position](#position) | The destination of the object that the player wants to move to. |

### attack
Prerequisite: game has started and the player is qualified for doing action.

Send when client wants to use his object to attack another object.

```jsonc
{
    "id": "attack",
    "data": {
        "original_position": [x1, y1],
        "target_position": [x2, y2]
    }
}
```

| Field | Type | Description |
| --- | --- | --- |
| original_position | [position](#position) | The position of the object that attacks. |
| target_position | [position](#position) | The position of the object that the player wants to attack. |

### end_round
Prerequisite: game has started and it is in current player's round.

Send when client finishes his action in his round.

**No data required**

```jsonc
{
    "id": "end_round",
    "data": {}
}
```

### surrender
Prerequisite: game has started.

Send when client wants to give up the game.

**No data required**

```jsonc
{
    "id": "surrender",
    "data": {}
}
```

# Server
Every message that the server send to the client would comply with the format below.

## server_message_id
- [action_failed](#action_failed)
- [joined_room](#joined_room)
- [game_start](#game_start)
- [round_start](#round_start)
- [attack_stage_start](#attack_stage_start)
- [draw](#draw)
- [use](#use)
- [object_move](#object_move)
- [object_attack](#object_attack)
- [object_status_changed](#object_status_changed)
- [object_existence_changed](#object_existence_changed)
- [effect_activated](#effect_activated)
- [result](#result)

### action_failed
Send when action failed. (joining room, using card etc.)

```jsonc
{
    "id": "action_failed",
    "data": {
        "message": "No target."
    }
}
```

| Field | Type | Description |
| --- | --- | --- |
| message | string | The message about the failed action. |

### joined_room
Return room information.

```jsonc
{
    "id": "joined_room",
    "data": {
        "room_number": 123,
        "enemy_name": "p2"
    }
}
```

| Field | Type | Description |
| --- | --- | --- |
| room_number | integer | The number of the room that player just joined. |
| enemy_name | string | Enemy's name. If there is no enemy yet, value would be empty string (""). |

### game_start
Announce clients that game starts.

```jsonc
{
    "id": "game_start",
    "data": {
        "first_player": "player",
        "player": {
            "name": "p1",
            "role": "player",
            "hand": 3,
            "deck": 37,
            "left_cost": 3,
            "max_cost": 3,
            "hand_cards": []
        },
        "enemy": {
            "name": "p2",
            "role": "enemy",
            "hand": 3,
            "deck": 37,
            "left_cost": 3,
            "max_cost": 3,
        },
        "initial_cards": [
            {
                "id": 1,
                "name": "sample",
                "type": "sample",
                "cost": 0,
                "color": "transparent",
                "description": "sample card 1",
                "effects": []
            },
            {
                "id": 2,
                "name": "sample2",
                "type": "sample",
                "cost": 0,
                "color": "transparent",
                "description": "sample card 2",
                "effects": []
            },
            {
                "id": 3,
                "name": "sample3",
                "type": "sample",
                "cost": 0,
                "color": "transparent",
                "description": "sample card 3",
                "effects": []
            }
        ]
    }
}
```

| Field | Type | Description |
| --- | --- | --- |
| first_player | string | The player who is randomly selected to go first. <br> Value: `player` or `enemy`. |
| player | [player](#player) | The player. |
| enemy | [player](#player) | The player's enemy. |
| initial_cards | [card](#card)[] | Initial cards for player to choose. |

### round_start
Announce clients that round starts.

```jsonc
{
    "id": "round_start",
    "data": {
        "turn": 1,
        "player": player,
        "enemy": player,
        "whose": "player"
    }
}
```
*Some fields are not fully expanded.*

| Field | Type | Description |
| --- | --- | --- |
| turn | integer | current turn number. |
| player | [player](#player) | The player status. |
| enemy | [player](#player) | The enemy status. |
| whose | string | Whose turn? <br>Value: `player` or `enemy`. |

### attack_stage_start
Announce clients that attack stage starts.

```jsonc
{
    "id": "attack_stage_start",
    "data": {
        "turn": 1,
        "player": player,
        "enemy": player,
        "whose": "enemy"
    }
}
```
*Some fields are not fully expanded.*

| Field | Type | Description |
| --- | --- | --- |
| turn | integer | current turn number. |
| player | [player](#player) | The player status. |
| enemy | [player](#player) | The enemy status. |
| whose | string | Whose turn? <br>Value: `player` or `enemy`. |

### draw
Someone draws card.

```jsonc
{
    "id": "draw",
    "data": {
        "count": 1,
        "player_role": "player",
        "player_status": player,
        "cards": [card] // optional
    }
}
```
*Some fields are not fully expanded.*

| Field | Type | Description |
| --- | --- | --- |
| count | integer | The number of how many cards player drew. |
| player_role | string | Role of player who drew. <br> Value: `player` or `enemy`. |
| player_status | [player](#player) | Status of player who drew. |
| cards (Optional) | [card](#card)[] | Cards that have been drawn. <br> This field only appears if player role is `player`. |


### use
Someone uses card.

```jsonc
{
    "id": "use",
    "data": {
        "player_role": "player",
        "player_status": player,
        "card": card,
        "target_position": position // optional
    }
}
```
*Some fields are not fully expanded.*

| Field | Type | Description |
| --- | --- | --- |
| player_role | string | Role of player who used card. <br> Value: `player` or `enemy`. |
| player_status | [player](#player) | Status of player who used card. |
| card | [card](#card) | Cards that have been used. |
| target_position (Optional) | [position](#position) | This field appears if the used card is assigned with target position. |

### object_move

```jsonc
{
    "id": "object_move",
    "data": {
        "player_role": "player",
        "player_status": player,
        "original_position": position,
        "target_position": position
    }
}
```
*Some fields are not fully expanded.*

| Field | Type | Description |
| --- | --- | --- |
| player_role | string | Role of player who ordered an attack. <br> Value: `player` or `enemy`. |
| player_status | [player](#player) | Status of player who ordered an attack. |
| original_position | [position](#position) | The original position of the object that the player wants to move. |
| target_position | [position](#position) | The destination of the object that the player wants to move to. |

### object_attack

```jsonc
{
    "id": "object_attack",
    "data": {
        "player_role": "player",
        "player_status": player,
        "original_position": position,
        "target_position": position
    }
}
```
*Some fields are not fully expanded.*

| Field | Type | Description |
| --- | --- | --- |
| player_role | string | Role of player who ordered an attack. <br> Value: `player` or `enemy`. |
| player_status | [player](#player) | Status of player who ordered an attack. |
| original_position | [position](#position) | The position of the object that attacks. |
| target_position | [position](#position) | The position of the object that the player wants to attack. |

### object_status_changed
Object status has changed.

- For type: `hp`, `atk`
```jsonc
{
    "id": "object_status_changed",
    "data": {
        "position": position,
        "type": "hp",
        "value": -1
    }
}
```
*Some fields are not fully expanded.*

| Field | Type | Description |
| --- | --- | --- |
| position | [position](#position) | Object position. |
| type | string | Changed status type. <br> Value: `hp`, `atk` |
| value | integer | Status change value. |

- For type: `add_effect`, `remove_effect`
```jsonc
{
    "id": "object_status_changed",
    "data": {
        "position": position,
        "type": "add_effect",
        "effect": effect
    }
}
```
*Some fields are not fully expanded.*

| Field | Type | Description |
| --- | --- | --- |
| position | [position](#position) | Object position. |
| type | string | Changed effect. <br> Value: `add_effect`, `remove_effect` |
| effect | [effect](#effect) | Effect change. |

- For type: `attack_range`
```jsonc
{
    "id": "object_status_changed",
    "data": {
        "position": position,
        "type": "attack_range",
        "attack_range": [position]
    }
}
```
*Some fields are not fully expanded.*

| Field | Type | Description |
| --- | --- | --- |
| position | [position](#position) | Object position. |
| type | string | Changed status type. <br> Value: `attack_range` |
| attack_range | [position](#position)[] | Full attack range after changing. |

### object_existence_changed
Send when object existence changes.

```jsonc
{
    "id": "object_existence_changed",
    "data": {
        "type": "summon",
        "object": object, // optional
        "position": position,
    }
}
```
*Some fields are not fully expanded.*

| Field | Type | Description |
| --- | --- | --- |
| type | string | Value: `summon`, `die` |
| object (Optional) | [object](#object) | Summoned Object. <br> This field appears if type is `summon`. |
| position | [position](#position) | Object position. |

### effect_activated
Send when an effect has activated.

```jsonc
{
    "id": "effect_activated",
    "data": {
        "position": position
    }
}
```
*Some fields are not fully expanded.*

| Field | Type | Description |
| --- | --- | --- |
| position | [position](#position) | Position of the object that activated an effect. |

### result
Game result.

```jsonc
{
    "id": "result",
    "data": {
        "result": "win"
    }
}
```

| Field | Type | Description |
| --- | --- | --- |
| result | string | The game result of current player.<br>Value: "win" or "lose". |
