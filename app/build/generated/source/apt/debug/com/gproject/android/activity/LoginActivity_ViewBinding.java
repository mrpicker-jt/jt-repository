// Generated code from Butter Knife. Do not modify!
package com.gproject.android.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.gproject.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    this.target = target;

    target.idEdit = Utils.findRequiredViewAsType(source, R.id.login_studentid, "field 'idEdit'", EditText.class);
    target.passwordEdit = Utils.findRequiredViewAsType(source, R.id.login_password, "field 'passwordEdit'", EditText.class);
    target.loginBtn = Utils.findRequiredViewAsType(source, R.id.login_loginbtn, "field 'loginBtn'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.idEdit = null;
    target.passwordEdit = null;
    target.loginBtn = null;
  }
}
