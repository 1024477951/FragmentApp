package cn.jiguang.imui.chatinput;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.Space;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.jiguang.imui.chatinput.camera.CameraNew;
import cn.jiguang.imui.chatinput.camera.CameraOld;
import cn.jiguang.imui.chatinput.camera.CameraSupport;
import cn.jiguang.imui.chatinput.camera.ImagePicker;
import cn.jiguang.imui.chatinput.camera.ui.ImageBaseActivity;
import cn.jiguang.imui.chatinput.camera.ui.ImageGridActivity;
import cn.jiguang.imui.chatinput.emoji.Constants;
import cn.jiguang.imui.chatinput.emoji.EmojiBean;
import cn.jiguang.imui.chatinput.emoji.EmojiView;
import cn.jiguang.imui.chatinput.emoji.listener.EmoticonClickListener;
import cn.jiguang.imui.chatinput.emoji.data.EmoticonEntity;
import cn.jiguang.imui.chatinput.emoji.widget.EmoticonsEditText;
import cn.jiguang.imui.chatinput.emoji.EmoticonsKeyboardUtils;
import cn.jiguang.imui.chatinput.listener.CameraControllerListener;
import cn.jiguang.imui.chatinput.listener.CameraEventListener;
import cn.jiguang.imui.chatinput.listener.OnCameraCallbackListener;
import cn.jiguang.imui.chatinput.listener.OnClickEditTextListener;
import cn.jiguang.imui.chatinput.listener.OnFileSelectedListener;
import cn.jiguang.imui.chatinput.listener.OnMenuClickListener;
import cn.jiguang.imui.chatinput.listener.RecordVoiceListener;
import cn.jiguang.imui.chatinput.model.FileItem;
import cn.jiguang.imui.chatinput.model.VideoItem;
import cn.jiguang.imui.chatinput.photo.SelectPhotoView;
import cn.jiguang.imui.chatinput.record.ProgressButton;
import cn.jiguang.imui.chatinput.record.RecordControllerView;
import cn.jiguang.imui.chatinput.record.RecordVoiceButton;
import cn.jiguang.imui.chatinput.utils.SimpleCommonUtils;

public class ChatInputView extends LinearLayout
        implements View.OnClickListener, TextWatcher, RecordControllerView.OnRecordActionListener,
        OnFileSelectedListener, CameraEventListener, ViewTreeObserver.OnPreDrawListener {

    private static final String TAG = "ChatInputView";
    private EmoticonsEditText mChatInput;
    //    private TextView mSendCountTv;
    private CharSequence mInput;
    private Space mInputMarginLeft;
    private Space mInputMarginRight;

    private ImageButton mVoiceBtn;
    private ImageButton mPhotoBtn;
    private ImageButton mCameraBtn;
    private ImageButton mEmojiBtn;
    private FrameLayout mSendBtn;

    private LinearLayout mChatInputContainer;
    private LinearLayout mMenuItemContainer;
    private FrameLayout mMenuContainer;
    private RelativeLayout mRecordVoiceRl;
    private LinearLayout mPreviewPlayLl;
    private ProgressButton mPreviewPlayBtn;
    private Button mSendAudioBtn;
    private Button mCancelSendAudioBtn;
    private LinearLayout mRecordContentLl;
    private RecordControllerView mRecordControllerView;
    private Chronometer mChronometer;
    private TextView mRecordHintTv;
    private RecordVoiceButton mRecordVoiceBtn;

    SelectPhotoView mSelectPhotoView;

    private OnMenuClickListener mListener;
    private OnCameraCallbackListener mCameraListener;
    private OnClickEditTextListener mEditTextListener;
    private CameraControllerListener mCameraControllerListener;
    private RecordVoiceListener mRecordVoiceListener;

    private EmojiView mEmojiRl;

    private ChatInputStyle mStyle;

    private InputMethodManager mImm;
    private Window mWindow;

    private int mWidth;
    private int mHeight;
    private int mSoftKeyboardHeight;
    private int mNowh;
    private int mOldh;
    public static int sMenuHeight = 831;

    private boolean mPendingShowMenu;

    private long mRecordTime;
    private boolean mPlaying = false;

    // To judge if it is record video mode
    private boolean mIsRecordVideoMode = false;

    // To judge if it is recording video now
    private boolean mIsRecordingVideo = false;

    // To judge if is finish recording video
    private boolean mFinishRecordingVideo = false;

    // Video file to be saved at
    private String mVideoFilePath;
    private int mVideoDuration;

    // If audio file has been set
    private boolean mSetData;
    private FileInputStream mFIS;
    private FileDescriptor mFD;
    private boolean mIsEarPhoneOn;
    private File mPhoto;
    private CameraSupport mCameraSupport;
    private int mCameraId = -1;
    private boolean mIsBackCamera = true;
    private boolean mIsFullScreen = false;
    private Context mContext;
    private Rect mRect = new Rect();
    public ImagePicker imagePicker;

    public ChatInputView(Context context) {
        super(context);
        init(context);
    }

    public ChatInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ChatInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context) {
        mContext = context;
        inflate(context, R.layout.view_chatinput, this);
        imagePicker = ImagePicker.getInstance();
        // menu buttons
        mChatInput = (EmoticonsEditText) findViewById(R.id.aurora_et_chat_input);
        mVoiceBtn = (ImageButton) findViewById(R.id.aurora_menuitem_ib_voice);
        mPhotoBtn = (ImageButton) findViewById(R.id.aurora_menuitem_ib_photo);
        mCameraBtn = (ImageButton) findViewById(R.id.aurora_menuitem_ib_camera);
        mEmojiBtn = (ImageButton) findViewById(R.id.aurora_menuitem_ib_emoji);
        mSendBtn = findViewById(R.id.rc_emoticon_toggle);

        View voiceBtnContainer = findViewById(R.id.aurora_framelayout_menuitem_voice);
        View photoBtnContainer = findViewById(R.id.aurora_framelayout_menuitem_photo);
        View cameraBtnContainer = findViewById(R.id.aurora_framelayout_menuitem_camera);
        View emojiBtnContainer = findViewById(R.id.aurora_framelayout_menuitem_emoji);
        voiceBtnContainer.setOnClickListener(onMenuItemClickListener);
        photoBtnContainer.setOnClickListener(onMenuItemClickListener);
        cameraBtnContainer.setOnClickListener(onMenuItemClickListener);
        emojiBtnContainer.setOnClickListener(onMenuItemClickListener);
        mSendBtn.setOnClickListener(onMenuItemClickListener);

//        mSendCountTv = (TextView) findViewById(R.id.aurora_menuitem_tv_send_count);
        mInputMarginLeft = (Space) findViewById(R.id.aurora_input_margin_left);
        mInputMarginRight = (Space) findViewById(R.id.aurora_input_margin_right);
        mChatInputContainer = (LinearLayout) findViewById(R.id.aurora_ll_input_container);
        mMenuItemContainer = (LinearLayout) findViewById(R.id.aurora_ll_menuitem_container);
        mMenuContainer = (FrameLayout) findViewById(R.id.aurora_fl_menu_container);
        mRecordVoiceRl = (RelativeLayout) findViewById(R.id.aurora_rl_recordvoice_container);
        mPreviewPlayLl = (LinearLayout) findViewById(R.id.aurora_ll_recordvoice_preview_container);
        mPreviewPlayBtn = (ProgressButton) findViewById(R.id.aurora_pb_recordvoice_play_audio);
        mRecordContentLl = (LinearLayout) findViewById(R.id.aurora_ll_recordvoice_content_container);

        mRecordControllerView = (RecordControllerView) findViewById(R.id.aurora_rcv_recordvoice_controller);
        mChronometer = (Chronometer) findViewById(R.id.aurora_chronometer_recordvoice);
        mRecordHintTv = (TextView) findViewById(R.id.aurora_tv_recordvoice_hint);
        mSendAudioBtn = (Button) findViewById(R.id.aurora_btn_recordvoice_send);
        mCancelSendAudioBtn = (Button) findViewById(R.id.aurora_btn_recordvoice_cancel);
        mRecordVoiceBtn = (RecordVoiceButton) findViewById(R.id.aurora_rvb_recordvoice_record);

        mSelectPhotoView = (SelectPhotoView) findViewById(R.id.aurora_view_selectphoto);
        mSelectPhotoView.setOnFileSelectedListener(this);
        mSelectPhotoView.initData();
        mEmojiRl = (EmojiView) findViewById(R.id.aurora_rl_emoji_container);

        mMenuContainer.setVisibility(GONE);

        mChatInput.addTextChangedListener(this);
        mChatInput.setOnBackKeyClickListener(new EmoticonsEditText.OnBackKeyClickListener() {
            @Override
            public void onBackKeyClick() {
                if (mMenuContainer.getVisibility() == VISIBLE) {
                    dismissMenuLayout();
                } else if (isKeyboardVisible()) {
                    EmoticonsKeyboardUtils.closeSoftKeyboard(mChatInput);
                }
            }
        });

        mRecordVoiceBtn.setRecordController(mRecordControllerView);
        mPreviewPlayBtn.setOnClickListener(this);
        mCancelSendAudioBtn.setOnClickListener(this);
        mSendAudioBtn.setOnClickListener(this);

        mImm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        mWindow = ((Activity) context).getWindow();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mRecordControllerView.setWidth(mWidth);
        mRecordControllerView.setOnControllerListener(this);
        getViewTreeObserver().addOnPreDrawListener(this);
    }

    EmoticonClickListener emoticonClickListener = new EmoticonClickListener() {
        @Override
        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {

            if (isDelBtn) {
                SimpleCommonUtils.delClick(mChatInput);
            } else {
                if (o == null) {
                    return;
                }
                if (actionType == Constants.EMOTICON_CLICK_BIGIMAGE) {
//                    if(o instanceof EmoticonEntity){
//                        OnSendImage(((EmoticonEntity)o).getIconUri());
//                    }
                } else {
                    String content = null;
                    if (o instanceof EmojiBean) {
                        content = ((EmojiBean) o).emoji;
                    } else if (o instanceof EmoticonEntity) {
                        content = ((EmoticonEntity) o).getContent();
                    }

                    if (TextUtils.isEmpty(content)) {
                        return;
                    }
                    int index = mChatInput.getSelectionStart();
                    Editable editable = mChatInput.getText();
                    editable.insert(index, content);
                }
            }
        }
    };

    private void init(Context context, AttributeSet attrs) {
        init(context);
        mStyle = ChatInputStyle.parse(context, attrs);

        mChatInput.setMaxLines(mStyle.getInputMaxLines());
        mChatInput.setHint(mStyle.getInputHint());
        mChatInput.setText(mStyle.getInputText());
        mChatInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, mStyle.getInputTextSize());
        mChatInput.setTextColor(mStyle.getInputTextColor());
        mChatInput.setHintTextColor(mStyle.getInputHintColor());
        mChatInput.setBackgroundResource(mStyle.getInputEditTextBg());
        mChatInput.setPadding(mStyle.getInputPaddingLeft(), mStyle.getInputPaddingTop(),
                mStyle.getInputPaddingRight(), mStyle.getInputPaddingBottom());
        mInputMarginLeft.getLayoutParams().width = mStyle.getInputMarginLeft();
        mInputMarginRight.getLayoutParams().width = mStyle.getInputMarginRight();
        mVoiceBtn.setImageResource(mStyle.getVoiceBtnIcon());
        mVoiceBtn.setBackground(mStyle.getVoiceBtnBg());
        mPhotoBtn.setBackground(mStyle.getPhotoBtnBg());
        mPhotoBtn.setImageResource(mStyle.getPhotoBtnIcon());
        mCameraBtn.setBackground(mStyle.getCameraBtnBg());
        mCameraBtn.setImageResource(mStyle.getCameraBtnIcon());

        SimpleCommonUtils.initEmoticonsEditText(mChatInput);
        mEmojiRl.setAdapter(SimpleCommonUtils.getCommonAdapter(mContext, emoticonClickListener));
    }

    public void setMenuClickListener(OnMenuClickListener listener) {
        mListener = listener;
    }

    public void setRecordVoiceListener(RecordVoiceListener listener) {
        this.mRecordVoiceBtn.setRecordVoiceListener(listener);
        this.mRecordVoiceListener = listener;
    }

    public void setOnCameraCallbackListener(OnCameraCallbackListener listener) {
        mCameraListener = listener;
    }

    public void setCameraControllerListener(CameraControllerListener listener) {
        mCameraControllerListener = listener;
    }

    public void setOnClickEditTextListener(OnClickEditTextListener listener) {
        mEditTextListener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mInput = s;

        if (mSelectPhotoView.getSelectFiles() == null || mSelectPhotoView.getSelectFiles().size() == 0) {
            if (s.length() >= 1 && start == 0 && before == 0) { // Starting input
                triggerSendButtonAnimation(mSendBtn, true, false);
            } else if (s.length() == 0 && before >= 1) { // Clear content
                triggerSendButtonAnimation(mSendBtn, false, false);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public EditText getInputView() {
        return mChatInput;
    }

    public RecordVoiceButton getRecordVoiceButton() {
        return mRecordVoiceBtn;
    }

    private OnClickListener onMenuItemClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
//            mChatInput.clearFocus();
//            EmoticonsKeyboardUtils.closeSoftKeyboard(mChatInput);
            setMenuContainerHeight((int) getResources().getDimension(R.dimen.d660_0));
            if (view.getId() == R.id.rc_emoticon_toggle) {
                // Allow send text and photos at the same time.
                if (onSubmit()) {
                    mChatInput.setText("");
                }
                if (mSelectPhotoView.getSelectFiles() != null && mSelectPhotoView.getSelectFiles().size() > 0) {
                    mListener.onSendFiles(mSelectPhotoView.getSelectFiles());

//                    mSendBtn.setImageDrawable(ContextCompat.getDrawable(getContext(),
//                            R.drawable.aurora_menuitem_send));
                    mSendBtn.setSelected(false);
//                    mSendCountTv.setVisibility(View.INVISIBLE);
                    mSelectPhotoView.resetCheckState();
                    dismissMenuLayout();
                    mImm.hideSoftInputFromWindow(getWindowToken(), 0);
                    mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                }

            } else {
                mChatInput.clearFocus();
                if (view.getId() == R.id.aurora_framelayout_menuitem_voice) {
                    if (mListener != null && mListener.switchToMicrophoneMode()) {
                        if (mRecordVoiceRl.getVisibility() == VISIBLE && mMenuContainer.getVisibility() == VISIBLE) {
                            dismissMenuLayout();
                        } else if (isKeyboardVisible()) {
                            mPendingShowMenu = true;
                            EmoticonsKeyboardUtils.closeSoftKeyboard(mChatInput);
                            showRecordVoiceLayout();
                        } else {
                            showMenuLayout();
                            showRecordVoiceLayout();
                        }
                    }
                } else if (view.getId() == R.id.aurora_framelayout_menuitem_photo) {
                    if (mListener != null && mListener.switchToGalleryMode()) {
                        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        if (mSelectPhotoView.getVisibility() == VISIBLE && mMenuContainer.getVisibility() == VISIBLE) {
                            dismissMenuLayout();
                        } else if (isKeyboardVisible()) {
                            mPendingShowMenu = true;
                            EmoticonsKeyboardUtils.closeSoftKeyboard(mChatInput);
                            showSelectPhotoLayout();
                        } else {
                            showMenuLayout();
                            showSelectPhotoLayout();
                        }
                    }
                } else if (view.getId() == R.id.aurora_framelayout_menuitem_camera) {
                    EmoticonsKeyboardUtils.closeSoftKeyboard(mChatInput);
                    if (callBack != null)
                        callBack.camera();
//                    if (mListener != null && mListener.switchToCameraMode()) {
//                        if (mMenuContainer.getVisibility() == VISIBLE) {
//                            dismissMenuLayout();
//                        } else if (isKeyboardVisible()) {
//                            mPendingShowMenu = true;
//                            showCameraLayout();
//                        } else {
//                            showMenuLayout();
//                            showCameraLayout();
//                        }
//                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                            if (mCameraSupport == null) {
////                                initCamera();
//                                if (callBack != null)
//                                    callBack.camera();
//                            }
//                        } else {
//                            Toast.makeText(getContext(), getContext().getString(R.string.sdcard_not_exist_toast),
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
                } else if (view.getId() == R.id.aurora_framelayout_menuitem_emoji) {
                    if (mListener != null && mListener.switchToEmojiMode()) {
                        if (mEmojiRl.getVisibility() == VISIBLE && mMenuContainer.getVisibility() == VISIBLE) {
                            dismissMenuLayout();
                        } else if (isKeyboardVisible()) {
                            mPendingShowMenu = true;
                            EmoticonsKeyboardUtils.closeSoftKeyboard(mChatInput);
                            showEmojiLayout();
                        } else {
                            showMenuLayout();
                            showEmojiLayout();
                        }
                    }
                }
            }
        }
    };

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.aurora_pb_recordvoice_play_audio) {
            // press preview play audio button
            if (!mPlaying) {
                if (mSetData) {
                    mPreviewPlayBtn.startPlay();
                    mPlaying = true;
                    mChronometer.setBase(convertStrTimeToLong(mChronometer.getText().toString()));
                    mChronometer.start();
                } else {
                    playVoice();
                }
            } else {
                mSetData = true;
                mChronometer.stop();
                mPlaying = false;
                mPreviewPlayBtn.stopPlay();
            }

        } else if (view.getId() == R.id.aurora_btn_recordvoice_cancel) {
            // preview play audio widget cancel sending audio
            mPreviewPlayLl.setVisibility(GONE);
            mRecordContentLl.setVisibility(VISIBLE);
            mRecordVoiceBtn.cancelRecord();
            mChronometer.setText("00:00");
            if (mRecordVoiceListener != null) {
                mRecordVoiceListener.onPreviewCancel();
            }

        } else if (view.getId() == R.id.aurora_btn_recordvoice_send) {
            // preview play audio widget send audio
            mPreviewPlayLl.setVisibility(GONE);
            dismissMenuLayout();
            mRecordVoiceBtn.finishRecord();
            mChronometer.setText("00:00");
            if (mRecordVoiceListener != null) {
                mRecordVoiceListener.onPreviewSend();
            }

        }
    }

    // play audio
    private void playVoice() {
        try {
            mFIS = new FileInputStream(mRecordVoiceBtn.getRecordFile());
            mFD = mFIS.getFD();
        } catch (Exception e) {
            Toast.makeText(getContext(), getContext().getString(R.string.file_not_found_toast),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            try {
                if (mFIS != null) {
                    mFIS.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void playVideo() {
        try {
            mCameraSupport.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Full screen mode
     */
    private void fullScreen() {
        // hide top status bar
        Activity activity = (Activity) getContext();
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        mChatInputContainer.setVisibility(GONE);
        mMenuItemContainer.setVisibility(GONE);
        int height = mHeight;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = mWindow.getWindowManager().getDefaultDisplay();
            DisplayMetrics dm = getResources().getDisplayMetrics();
            display.getRealMetrics(dm);
            height = dm.heightPixels;
        }

        mMenuContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
        mIsFullScreen = true;
    }

    public int dp2px(float value) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (value * scale + 0.5f);
    }

    /**
     * Recover screen
     */
    private void recoverScreen() {
        final Activity activity = (Activity) getContext();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
                attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                activity.getWindow().setAttributes(attrs);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                mIsFullScreen = false;
                mChatInputContainer.setVisibility(VISIBLE);
                mMenuItemContainer.setVisibility(VISIBLE);
                int height = sMenuHeight;
                if (mSoftKeyboardHeight != 0) {
                    height = mSoftKeyboardHeight;
                }
                setMenuContainerHeight(height);
            }
        });
    }

    public void dismissMenuLayout() {
        mMenuContainer.setVisibility(GONE);
        if (mCameraSupport != null) {
            mCameraSupport.release();
            mCameraSupport = null;
        }
    }

    public void showMenuLayout() {
        EmoticonsKeyboardUtils.closeSoftKeyboard(mChatInput);
        mMenuContainer.setVisibility(VISIBLE);
    }

    public void showRecordVoiceLayout() {
        mSelectPhotoView.setVisibility(GONE);
        mEmojiRl.setVisibility(GONE);
        mRecordVoiceRl.setVisibility(VISIBLE);
        mRecordContentLl.setVisibility(VISIBLE);
        mPreviewPlayLl.setVisibility(GONE);
    }

    public void showSelectPhotoLayout() {
        mRecordVoiceRl.setVisibility(GONE);
        mEmojiRl.setVisibility(GONE);
        mSelectPhotoView.setVisibility(VISIBLE);
    }

    public void showCameraLayout() {
        mRecordVoiceRl.setVisibility(GONE);
        mSelectPhotoView.setVisibility(GONE);
        mEmojiRl.setVisibility(GONE);
    }

    public void dismissCameraLayout() {
        if (mCameraSupport != null) {
            mCameraSupport.release();
            mCameraSupport = null;
        }
    }

    public void showEmojiLayout() {
        mRecordVoiceRl.setVisibility(GONE);
        mSelectPhotoView.setVisibility(GONE);
        mEmojiRl.setVisibility(VISIBLE);
    }

    /**
     * 设置菜单容器的高度，初始化菜单后调用此方法。**@Param高度菜单高度，设置与软键盘相同的高度，以使显示达到完美。
     */
    public void setMenuContainerHeight(int height) {
        if (height > 0) {
            sMenuHeight = height;
            ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
            mMenuContainer.setLayoutParams(params);
        }
    }

    private boolean onSubmit() {
        return mListener != null && mListener.onSendTextMessage(mInput);
    }

    public int getMenuState() {
        return mMenuContainer.getVisibility();
    }

    /**
     * Select photo callback
     */
    @Override
    public void onFileSelected() {
        int size = mSelectPhotoView.getSelectFiles().size();
        Log.i("ChatInputView", "select file size: " + size);
        if (mInput.length() == 0 && size == 0) {
//            triggerSendButtonAnimation(mSendBtn, true, true);
            mSendBtn.setSelected(false);
        } else if (mInput.length() > 0 || size > 0) {
//            mSendCountTv.setVisibility(View.VISIBLE);
            mSendBtn.setSelected(true);
        }
//        mSendCountTv.setText(String.valueOf(size));
        mSelectPhotoView.setCheckNum(size);
    }

    /**
     * Cancel select photo callback
     */
    @Override
    public void onFileDeselected() {
        int size = mSelectPhotoView.getSelectFiles().size();
        Log.i("ChatInputView", "deselect file size: " + size);
        if (size > 0) {
//            mSendCountTv.setText(String.valueOf(size));
            mSendBtn.setSelected(true);
        } else {
//            mSendCountTv.setVisibility(View.INVISIBLE);
            if (mInput.length() == 0) {
//                triggerSendButtonAnimation(mSendBtn, false, true);
                mSendBtn.setSelected(false);
            }
        }
        mSelectPhotoView.setCheckNum(size);
    }

    /**
     * Trigger send button animation
     *
     * @param sendBtn       send button
     * @param hasContent    EditText has content or photos have been selected
     * @param isSelectPhoto check if selecting photos
     */
    private void triggerSendButtonAnimation(final View sendBtn, final boolean hasContent,
                                            final boolean isSelectPhoto) {
        float[] shrinkValues = new float[]{0.6f};
        AnimatorSet shrinkAnimatorSet = new AnimatorSet();
        shrinkAnimatorSet.playTogether(ObjectAnimator.ofFloat(sendBtn, "scaleX", shrinkValues),
                ObjectAnimator.ofFloat(sendBtn, "scaleY", shrinkValues));
        shrinkAnimatorSet.setDuration(100);

        float[] restoreValues = new float[]{1.0f};
        final AnimatorSet restoreAnimatorSet = new AnimatorSet();
        restoreAnimatorSet.playTogether(ObjectAnimator.ofFloat(sendBtn, "scaleX", restoreValues),
                ObjectAnimator.ofFloat(sendBtn, "scaleY", restoreValues));
        restoreAnimatorSet.setDuration(100);

        restoreAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
//                mSendCountTv.bringToFront();
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    requestLayout();
                    invalidate();
                }
                if (mSelectPhotoView.getSelectFiles() != null && mSelectPhotoView.getSelectFiles().size() > 0) {
//                    mSendCountTv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        shrinkAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (!hasContent && isSelectPhoto) {
//                    mSendCountTv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (hasContent) {
//                    mSendBtn.setImageDrawable(ContextCompat.getDrawable(getContext(),
//                            mStyle.getSendBtnPressedIcon()));
                    mSendBtn.setSelected(true);
                } else {
//                    mSendBtn.setImageDrawable(ContextCompat.getDrawable(getContext(),
//                            R.drawable.aurora_menuitem_send));
                    mSendBtn.setSelected(false);
                }
                restoreAnimatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        shrinkAnimatorSet.start();
    }

    /**
     * Set camera capture file path and file name. If user didn't invoke this method, will save in
     * default path.
     *
     * @param path     Photo to be saved in.
     * @param fileName File name.
     */
    @Deprecated
    public void setCameraCaptureFile(String path, String fileName) {
        File destDir = new File(path);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        mPhoto = new File(path, fileName + ".png");
    }

    public void sendCameraImg(FileItem video) {
        List<FileItem> list = new ArrayList<>();
        list.add(video);
        if (mListener != null)
            mListener.onSendFiles(list);
    }

    /**
     * Record audio widget finger on touch record button callback
     */
    @Override
    public void onStart() {
        Log.e("ChatInputView", "starting chronometer");
        mRecordHintTv.setVisibility(VISIBLE);
        mRecordHintTv.setText("准备中");
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mChronometer.setBase(SystemClock.elapsedRealtime());
                mChronometer.start();
                mChronometer.setVisibility(VISIBLE);
                mRecordHintTv.setVisibility(INVISIBLE);
            }
        }, 200);
    }

    /**
     * Recording audio mode, finger moving callback
     */
    @Override
    public void onMoving() {
        mChronometer.setVisibility(VISIBLE);
        mRecordHintTv.setVisibility(INVISIBLE);
    }

    /**
     * Recording audio mode, finger moved left button (preview button) callback
     */
    @Override
    public void onMovedLeft() {
        mChronometer.setVisibility(INVISIBLE);
        mRecordHintTv.setVisibility(VISIBLE);
        mRecordHintTv.setText(getContext().getString(R.string.preview_play_audio_hint));
    }

    /**
     * Recording audio mode, finger moved right button (cancel button)
     */
    @Override
    public void onMovedRight() {
        mChronometer.setVisibility(INVISIBLE);
        mRecordHintTv.setVisibility(VISIBLE);
        mRecordHintTv.setText(getContext().getString(R.string.cancel_record_voice_hint));
    }

    /**
     * Recording audio mode, finger moved left button and release
     */
    @Override
    public void onLeftUpTapped() {
        mChronometer.stop();
        mRecordTime = SystemClock.elapsedRealtime() - mChronometer.getBase();
        mPreviewPlayBtn.setMax((int) (mRecordTime / 1000));
        mChronometer.setVisibility(VISIBLE);
        mRecordHintTv.setVisibility(INVISIBLE);
        mPreviewPlayLl.setVisibility(VISIBLE);
        mRecordContentLl.setVisibility(GONE);
    }

    @Override
    public void onMovedTop() {
        mChronometer.setVisibility(INVISIBLE);
        mRecordHintTv.setVisibility(VISIBLE);
        mRecordHintTv.setText(getContext().getString(R.string.cancel_record_voice_hint));
    }

    @Override
    public void onTopUpTapped() {
        mChronometer.stop();
        mChronometer.setVisibility(INVISIBLE);
        mRecordHintTv.setText(getContext().getString(R.string.record_voice_hint));
        mRecordHintTv.setVisibility(VISIBLE);
    }

    /**
     * Recording audio mode, finger moved right button and release
     */
    @Override
    public void onRightUpTapped() {
        mChronometer.stop();
        mChronometer.setVisibility(INVISIBLE);
        mRecordHintTv.setText(getContext().getString(R.string.record_voice_hint));
        mRecordHintTv.setVisibility(VISIBLE);
    }

    private long convertStrTimeToLong(String strTime) {
        String[] timeArray = strTime.split(":");
        long longTime = 0;
        if (timeArray.length == 2) { // If time format is MM:SS
            longTime = Integer.parseInt(timeArray[0]) * 60 * 1000 + Integer.parseInt(timeArray[1]) * 1000;
        }
        return SystemClock.elapsedRealtime() - longTime;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mCameraSupport != null) {
            mCameraSupport.release();
        }
        getViewTreeObserver().removeOnPreDrawListener(this);
    }

    @Override
    public void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == GONE) {
            if (mCameraSupport != null) {
                mCameraSupport.release();
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus && mHeight <= 0) {
            this.getRootView().getGlobalVisibleRect(mRect);
            mHeight = mRect.bottom;
            Log.d(TAG, "Window focus changed, height: " + mHeight);
        }
    }

    public boolean isKeyboardVisible() {
        return (getDistanceFromInputToBottom() > 300 && mMenuContainer.getVisibility() == GONE)
                || (getDistanceFromInputToBottom() > (mMenuContainer.getHeight() + 300)
                && mMenuContainer.getVisibility() == VISIBLE);
    }

    @Override
    public void onFinishTakePicture() {
        if (mIsFullScreen) {
            recoverScreen();
        }
    }

    public boolean isFullScreen() {
        return this.mIsFullScreen;
    }

    public void setPendingShowMenu(boolean flag) {
        this.mPendingShowMenu = flag;
    }

    @Override
    public boolean onPreDraw() {
        if (mPendingShowMenu) {
            if (isKeyboardVisible()) {
                /**注释为防止高度混乱问题*/
//                ViewGroup.LayoutParams params = mMenuContainer.getLayoutParams();
//                int distance = getDistanceFromInputToBottom();
//                Log.d(TAG, "Distance from bottom: " + distance);
//                if (distance > 300 && distance != params.height) {
//                    params.height = distance;
//                    mSoftKeyboardHeight = distance;
//                    mMenuContainer.setLayoutParams(params);
//                }
                return false;
            } else {
                showMenuLayout();
                mPendingShowMenu = false;
                return false;
            }
        } else {
            if (mMenuContainer.getVisibility() == VISIBLE && isKeyboardVisible()) {
                dismissMenuLayout();
                return false;
            }
        }
        return true;
    }

    public int getDistanceFromInputToBottom() {
        mSendBtn.getGlobalVisibleRect(mRect);//得到view相对于整个屏幕的坐标
        return mHeight - mRect.bottom;
    }

    private final Runnable measureAndLayout = new Runnable() {
        @Override
        public void run() {
            measure(
                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };

    @Override
    public void requestLayout() {
        super.requestLayout();

        // React Native Override requestLayout, since we refresh our layout in native, RN catch the
        //requestLayout event, so that the view won't refresh at once, we simulate layout here.
        post(measureAndLayout);
    }

    public int getSoftKeyboardHeight() {
        return this.mSoftKeyboardHeight;
    }

    public RelativeLayout getVoiceContainer() {
        return mRecordVoiceRl;
    }

    public FrameLayout getSelectPictureContainer() {
        return mSelectPhotoView;
    }

    public EmojiView getEmojiContainer() {
        return mEmojiRl;
    }

    public ChatInputStyle getStyle() {
        return this.mStyle;
    }

    public ImageButton getVoiceBtn() {
        return this.mVoiceBtn;
    }

    public ImageButton getPhotoBtn() {
        return this.mPhotoBtn;
    }

    public ImageButton getCameraBtn() {
        return this.mCameraBtn;
    }

    public ImageButton getEmojiBtn() {
        return this.mEmojiBtn;
    }

    private CallBack callBack;

    public interface CallBack {
        void camera();
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

}
