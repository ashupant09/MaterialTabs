package android.netcom.ashu.materialtabs.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by KamaL on 31-08-2016.
 */
public class AnimationUtils {

    public static void animate(RecyclerView.ViewHolder holder, boolean isDown){

        ObjectAnimator animationScaleX = ObjectAnimator.ofFloat(holder.itemView, "scaleX",0.8F, 1.0F);
        ObjectAnimator animationScaleY = ObjectAnimator.ofFloat(holder.itemView, "scaleY",1.0F);
        ObjectAnimator animationTranslationY = ObjectAnimator.ofFloat(holder.itemView, "translationY", isDown== true? 100 : -100, 0).setDuration(3000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AnticipateInterpolator());
        animatorSet.playTogether(animationScaleX,animationScaleY,animationTranslationY);
        animatorSet.start();

    }

    public static void animateToolbar(View containerToolbar) {

        containerToolbar.setRotationX(-90);
        containerToolbar.setAlpha(0.2F);
        containerToolbar.setPivotX(0.0F);
        containerToolbar.setPivotY(0.0F);
        Animator alpha = ObjectAnimator.ofFloat(containerToolbar, "alpha", 0.2F, 0.4F, 0.6F, 0.8F, 0.5F,0.6F, 1.0F).setDuration(4000);
        Animator rotationX = ObjectAnimator.ofFloat(containerToolbar, "rotationX", -90, 60, -45, 45, -10, 30, 0, 20, 0, 5, 0).setDuration(8000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(alpha, rotationX);
        animatorSet.start();
    }
}
