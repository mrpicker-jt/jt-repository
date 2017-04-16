// Generated code from Butter Knife. Do not modify!
package com.gproject.android.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.gproject.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TabActivity_ViewBinding implements Unbinder {
  private TabActivity target;

  @UiThread
  public TabActivity_ViewBinding(TabActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TabActivity_ViewBinding(TabActivity target, View source) {
    this.target = target;

    target.moniterBtn = Utils.findRequiredViewAsType(source, R.id.tab_monitor, "field 'moniterBtn'", TextView.class);
    target.modelBtn = Utils.findRequiredViewAsType(source, R.id.tab_modle, "field 'modelBtn'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TabActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.moniterBtn = null;
    target.modelBtn = null;
  }
}
