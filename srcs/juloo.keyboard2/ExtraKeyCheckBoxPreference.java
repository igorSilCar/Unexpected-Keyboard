package juloo.keyboard2;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.SharedPreferences;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import java.util.HashSet;
import java.util.Set;

public class ExtraKeyCheckBoxPreference extends CheckBoxPreference
{
  public static String[] extra_keys = new String[]
  {
    "accent_aigu",
    "accent_grave",
    "accent_double_aigu",
    "accent_dot_above",
    "accent_circonflexe",
    "accent_tilde",
    "accent_cedille",
    "accent_trema",
    "accent_ring",
    "accent_caron",
    "accent_macron",
    "accent_ogonek",
    "€",
    "ß",
    "£"
  };

  boolean _key_font;

  public ExtraKeyCheckBoxPreference(Context context, AttributeSet attrs)
  {
    super(context, attrs);
    final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExtraKeyCheckBoxPreference);
    int index = a.getInteger(R.styleable.ExtraKeyCheckBoxPreference_index, 0);
    a.recycle();
    String key_name = extra_keys[index];
    setKey(pref_key_of_key_name(key_name));
    KeyValue kv = KeyValue.getKeyByName(key_name);
    setTitle(kv.getString());
    _key_font = kv.hasFlags(KeyValue.FLAG_KEY_FONT);
  }

  @Override
  protected void onBindView(View view)
  {
    super.onBindView(view);
    TextView title = (TextView)view.findViewById(android.R.id.title);
    title.setTypeface(_key_font ? Theme.getKeyFont(getContext()) : null);
  }

  static String pref_key_of_key_name(String key_name)
  {
    return "extra_key_" + key_name;
  }

  public static Set<KeyValue> get_extra_keys(SharedPreferences prefs)
  {
    HashSet<KeyValue> ks = new HashSet<KeyValue>();
    for (String key_name : extra_keys)
    {
      if (prefs.getBoolean(pref_key_of_key_name(key_name), false))
        ks.add(KeyValue.getKeyByName(key_name));
    }
    return ks;
  }
}
