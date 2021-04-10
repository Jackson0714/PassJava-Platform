const fillIn = val => `${val < 10 ? '0' : ''}${val}`,
    formatTime = time => {
        let second = Math.round(time % 60),
            minute = Math.round(time / 60 % 60),
            hour = Math.round(time / 60 / 60);
        return `${fillIn(hour)}:${fillIn(minute)}:${fillIn(second)}`;
    };

class Audio{
    constructor(obj){
        const _ts = this,
            option = _ts.option = obj.attr;

        _ts.loop = option.loop === 'true',
        _ts.autoplay = option.autoplay === 'true';
        _ts.create();
        _ts.index = 0;

        
    }
    create(){
        const _ts = this,
            option = _ts.option;
        let audio = _ts.audio = wx.createInnerAudioContext();
        audio.src = option.src;

        // 说明可以播放了
        audio.onCanplay(function(){
            if(_ts.autoplay && !_ts.index){
                _ts.play();
            };
            if(!_ts.autoplay && !_ts.index){
                _ts.eventCanplay();
            };
        });

        // 更新时间
        audio.onTimeUpdate(function(){
            //_ts.status = 'update';
            _ts.duration = audio.duration;
            _ts.currentTime = audio.currentTime;

            // 定义播放结束
            if(_ts.duration - _ts.currentTime < 0.5){
                _ts.index++;
                if(_ts.loop){
                    audio.stop();
                }else{
                    _ts.stop();
                };
                audio.seek(0);
            };
            _ts.eventTimeUpdate(formatTime(_ts.duration),formatTime(_ts.currentTime));
        });

        // 
        audio.onSeeked(function(){
            if(_ts.loop){
                _ts.play();
            };
        });



    }
    // 播放
    play(){
        const _ts = this;
        _ts.status = 'play';
        _ts.audio.play();
        _ts.eventPlay();
    }
    // 暂停
    pause(){
        const _ts = this;
        _ts.status = 'pause';
        _ts.audio.pause();
        _ts.eventPause();
    }
    // 停止
    stop(){
        const _ts = this;
        _ts.status = 'stop';
        _ts.audio.stop();
        _ts.eventStop();
    }
    // 销毁
    destroy(){
        const _ts = this;
        _ts.stop();
        _ts.audio.destroy();
    }
    eventCanplay(){}
    eventTimeUpdate(){}
    eventEnded(){}
    eventError(){}
    eventPause(){}
    eventPlay(){}
    eventSeeked(){}
    eventSeeking(){}
    eventStop(){}
    eventWaiting(){}
};
module.exports = Audio;