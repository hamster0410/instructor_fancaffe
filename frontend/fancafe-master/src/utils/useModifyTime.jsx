import { useState, useEffect } from 'react';

export function useModifyTime(time) {
    const [modifiedTime, setModifiedTime] = useState('');

    useEffect(() => {
        if (!time) return; // 시간 값이 없으면 실행하지 않음

        function calculateTimeDifference() {
            const contentsDate = time; 
            const contentsDay = contentsDate.split('T')[0];  
            const contentsDayList = contentsDay.split('-');
            const contentsTime = contentsDate.split('T')[1].split('.')[0];
            const contentsDateObj = new Date(contentsDay + ' ' + contentsTime);

            const nowDateObj = new Date();
            const seoulOffset = 9 * 60;  
            nowDateObj.setMinutes(nowDateObj.getMinutes() + nowDateObj.getTimezoneOffset() + seoulOffset);

            const diffMs = nowDateObj - contentsDateObj; 

            const seconds = Math.floor(diffMs / 1000);
            const minutes = Math.floor(seconds / 60);
            const hours = Math.floor(minutes / 60);
            const days = Math.floor(hours / 24);

            let timeText = '';
            if (seconds < 60) {
                timeText = `${seconds}초전`;
            } else if (minutes < 60) {
                timeText = `${minutes}분전`;
            } else if (hours < 24) {
                timeText = `${hours}시간전`;
            } else if (days < 8) {
                timeText = `${days}일전`;
            } else if (days < 366) {
                timeText = `${contentsDayList[1]}.${contentsDayList[2]}`;
            } else {
                timeText = `${contentsDayList[0]}.${contentsDayList[1]}.${contentsDayList[2]}`;
            }

            setModifiedTime(timeText);
        }

        calculateTimeDifference();
    }, [time]); 

    return modifiedTime;
}