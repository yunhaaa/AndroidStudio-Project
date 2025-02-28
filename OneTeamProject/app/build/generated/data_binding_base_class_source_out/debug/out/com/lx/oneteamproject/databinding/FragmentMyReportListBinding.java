// Generated by view binder compiler. Do not edit!
package com.lx.oneteamproject.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.lx.oneteamproject.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentMyReportListBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView imageView9;

  @NonNull
  public final LinearLayout linearLayout12;

  @NonNull
  public final RecyclerView myreportlist;

  @NonNull
  public final TextView textView17;

  private FragmentMyReportListBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView imageView9, @NonNull LinearLayout linearLayout12,
      @NonNull RecyclerView myreportlist, @NonNull TextView textView17) {
    this.rootView = rootView;
    this.imageView9 = imageView9;
    this.linearLayout12 = linearLayout12;
    this.myreportlist = myreportlist;
    this.textView17 = textView17;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMyReportListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMyReportListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_my_report_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMyReportListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageView9;
      ImageView imageView9 = ViewBindings.findChildViewById(rootView, id);
      if (imageView9 == null) {
        break missingId;
      }

      id = R.id.linearLayout12;
      LinearLayout linearLayout12 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout12 == null) {
        break missingId;
      }

      id = R.id.myreportlist;
      RecyclerView myreportlist = ViewBindings.findChildViewById(rootView, id);
      if (myreportlist == null) {
        break missingId;
      }

      id = R.id.textView17;
      TextView textView17 = ViewBindings.findChildViewById(rootView, id);
      if (textView17 == null) {
        break missingId;
      }

      return new FragmentMyReportListBinding((ConstraintLayout) rootView, imageView9,
          linearLayout12, myreportlist, textView17);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
