package camp.codelab.pizzaorderapp

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val tip = sharedPreferences.getFloat("TIP", 0f)
        tipEditText.setText(tip.toString())

        pizzaRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            calculatePrice()
        }

        pepsiCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            calculatePrice()
        }

        garlicCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            calculatePrice()
        }

        friesCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            calculatePrice()
        }


        tipEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculatePrice()
            }

        })

    }


    override fun onStop() {
        super.onStop()

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = sharedPreferences.edit()
        val tipString = tipEditText.text.toString()
        if (tipString.isNotEmpty()) {
            val tip = tipString.toFloat()
            editor.putFloat("TIP", tip)
        } else {
            editor.putFloat("TIP", 0f)
        }

        editor.apply()

    }

    fun calculatePrice() {

        var price: Float = 0f

        when (pizzaRadioGroup.checkedRadioButtonId) {
            sausageRadioButton.id -> {
                price += 8f
            }
            cheeseRadioButton.id -> {
                price += 5f
            }
            appleRadioButton.id -> {
                price += 20f
            }
            olivesRadioButton.id -> {
                price += 6.5f
            }
        }

        if (pepsiCheckBox.isChecked) {
            price += 0.5f
        }

        if (garlicCheckBox.isChecked) {
            price += 0.2f
        }

        if (friesCheckBox.isChecked) {
            price += 2f
        }

        val tipString = tipEditText.text.toString()

        if (tipString.isNotEmpty()) {
            val tip = tipString.toFloat()
            price += tip
        }

        resultTextView.text = "$$price"

    }
}
