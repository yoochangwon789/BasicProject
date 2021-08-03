package com.yoochangwonspro.basicproject

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageButton

class RecodeButton(
    context: Context,
    attributeSet: AttributeSet
): AppCompatImageButton(context, attributeSet) {
    // 매년 안드로이드는 새로운 버젼이 출시되는데
    // 이전 버전의 대한 호환성이 필요한데 AppCompat 은 기존클래스를 랩핑해서
    // 이전 버전에서도 새로 출시한 기능들을 사용할 수 있게 지원해 주는 것이 AppCompat 라이브러리 이다
    // xml 에서 appcompat 을 쓰지 않는 경우는 xml 에서는 appcompat 을 자동적으로 맵핑 시켜주는 것이
    // 프로젝트 내부에서 설정되어 있다
    // 하지만 코드 부분에서는 따로 지원을 하지 않기 때문에 appcompat 을 사용하는 것이다

    fun updateIconWithState(state: State) {
        when (state) {
            State.BEFORE_RECORDING -> {
                setImageResource(R.drawable.ic_record)
            }
            State.ON_RECORDING -> {
                setImageResource(R.drawable.ic_stop)
            }
            State.AFTER_RECORDING ->{
                setImageResource(R.drawable.ic_play)
            }
            State.ON_PLAYING -> {
                setImageResource(R.drawable.ic_stop)
            }
        }
    }
}