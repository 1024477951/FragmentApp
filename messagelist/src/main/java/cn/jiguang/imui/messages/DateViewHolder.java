package cn.jiguang.imui.messages;

import android.view.View;
import android.widget.TextView;

import cn.jiguang.imui.R;
import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.view.RoundTextView;


public class DateViewHolder<MESSAGE extends IMessage>
        extends BaseMessageViewHolder<MESSAGE>
        implements MsgListAdapter.DefaultMessageViewHolder {

    private TextView mEvent;

    public DateViewHolder(View itemView, boolean isSender) {
        super(itemView);
        mEvent = itemView.findViewById(R.id.aurora_tv_msgitem_event);
    }

    @Override
    public void onBind(MESSAGE message) {
        mEvent.setText(message.getText());
    }

    @Override
    public void applyStyle(MessageListStyle style) {

    }
}
