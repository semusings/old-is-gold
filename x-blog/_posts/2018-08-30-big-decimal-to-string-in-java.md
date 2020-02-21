---
layout: post
title:  "BigDecimal to String in java"
author: developerbhuwan
categories: [ java, big-decimal ]
---

In Java, there are two very simple approaches to convert 
`BigDecimal` to `String` without stripping trailing zeros.

- Using `BigDecimal` scale
- Using `DecimalFormat`

## Example
 
{% highlight java %} 
public class BigDecimalToStringTest {

    @Test
    public void usingScale() {
        assertEquals("1000.000", numberToStringWithTrailZeros(1000.000, 3));
        assertEquals("1000.10", numberToStringWithTrailZeros(1000.10, 2));
    }

    @Test
    public void usingDecimalFormat() {
        DecimalFormat _3DigitDecimalFormat = new DecimalFormat("#0.000");
        DecimalFormat _2DigitDecimalFormat = new DecimalFormat("#0.00");

        assertEquals("1000.000", _3DigitDecimalFormat.format(new BigDecimal(1000.000)));
        assertEquals("1000.123", _3DigitDecimalFormat.format(new BigDecimal(1000.1234)));
        assertEquals("1000.10", _2DigitDecimalFormat.format(new BigDecimal(1000.10000)));
    }


    private String numberToStringWithTrailZeros(double number, int scaleDigits) {
        BigDecimal decimal = BigDecimal.valueOf(number);
        return decimal.setScale(scaleDigits, RoundingMode.HALF_UP).toPlainString();
    }

}
{% endhighlight %} 
