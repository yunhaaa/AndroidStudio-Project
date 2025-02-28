// Generated by view binder compiler. Do not edit!
package com.lx.oneteamproject.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.lx.oneteamproject.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class NearNaturalDisaterFragmentBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final FragmentContainerView naturalDisaterFragment;

  @NonNull
  public final TextView textView;

  private NearNaturalDisaterFragmentBinding(@NonNull ConstraintLayout rootView,
      @NonNull FragmentContainerView naturalDisaterFragment, @NonNull TextView textView) {
    this.rootView = rootView;
    this.naturalDisaterFragment = naturalDisaterFragment;
    this.textView = textView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static NearNaturalDisaterFragmentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static NearNaturalDisaterFragmentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.near_natural_disater_fragment, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static NearNaturalDisaterFragmentBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.naturalDisaterFragment;
      FragmentContainerView naturalDisaterFragment = ViewBindings.findChildViewById(rootView, id);
      if (naturalDisaterFragment == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      return new NearNaturalDisaterFragmentBinding((ConstraintLayout) rootView,
          naturalDisaterFragment, textView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
