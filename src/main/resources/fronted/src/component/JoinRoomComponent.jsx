import React from 'react';

function JoinRoomComponent({value, roomIdChanged, roomIdBtnHandler  }) {


    return (
        <div>
            <input
                type="text"
                value={value}
                onChange={roomIdChanged}
            />
            <button onClick={roomIdBtnHandler}>방 입장하기 </button>
        </div>
    );
}

export default JoinRoomComponent;