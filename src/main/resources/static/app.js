document.addEventListener("DOMContentLoaded", function() {
const attackerIdInput = document.getElementById('attackerId');
const defIdInput = document.getElementById('defenderId');
const posInput = document.getElementById('posOfShot');
    const shotsDiv = document.getElementById('shots');

    const socket = new SockJS('/websocket');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        stompClient.subscribe('/topic/shots', (message) => {
            const parsedShot = JSON.parse(message.body);
            const shotElement = document.createElement('p');
            shotElement.innerText = 'Attacker: ' + parsedShot.attackerId + ', Defender: ' + parsedShot.defenderId + ', Position: ' + parsedShot.posOfShot + ', Hit:' + parsedShot.hit;
            shotsDiv.appendChild(shotElement);
        });
    });

    function sendShot() {
const attackerId = attackerIdInput.value;
const defId = defIdInput.value;
const pos = posInput.value;



        if (attackerId && defId && pos) {
            const shot = {
                attackerId: attackerId,
                defenderId: defId,
                posOfShot: pos
            };

            stompClient.send('/app/game', {}, JSON.stringify(shot));

            attackerIdInput.value = '';
            defIdInput.value = '';
            posInput.value = '';
        }
    }

    const sendButton = document.querySelector("button");
    sendButton.addEventListener("click", sendShot);
});
