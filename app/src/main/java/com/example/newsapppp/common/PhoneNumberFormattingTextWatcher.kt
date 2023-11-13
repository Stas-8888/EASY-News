package com.example.newsapppp.common

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class PhoneNumberFormattingTextWatcher(private val editText: EditText) : TextWatcher {
        private var isFormatting = false

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (isFormatting) {
                return
            }

            isFormatting = true

            // Remove all non-digit characters from the text
            val phoneNumber = s?.toString()?.replace("\\D".toRegex(), "")

            if (phoneNumber.isNullOrEmpty()) {
                editText.setText("")
            } else {
                // Format the phone number as per your desired format
//                val formattedPhoneNumber = formatPhoneNumber(phoneNumber)
                val formattedPhoneNumber = formatPhoneNumber(phoneNumber)

                // Set the formatted phone number back to the EditText
                editText.setText(formattedPhoneNumber)

                // Move the cursor to the end of the formatted text
                editText.setSelection(formattedPhoneNumber.length)
            }

            isFormatting = false
        }

        private fun formatPhoneNumber(phoneNumber: String): String {
            // Implement your logic to format the phone number here.
            // For example, you can format it as (XXX) XXX-XXXX or XXX-XXX-XXXX
            // For simplicity, let's format it as XXX-XXX-XXXX here.

            val formattedNumber = StringBuilder()
            for (i in 0 until phoneNumber.length) {
                formattedNumber.append(phoneNumber[i])
                if (i == 2 || i == 5) {
                    formattedNumber.append(" ")
                }
            }

            return formattedNumber.toString()
        }
    }
