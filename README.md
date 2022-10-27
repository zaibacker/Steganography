# Steganography
Simple code to hide a picture into another one.

The purpose of the "stegano1.java" code is to hide an image inside another image.

The container image is coded on 32 bits per pixel, the first byte takes care of the brightness, the next 3 are the famous R, G, B. For each of them I keep 5 bits out of 8, that is to say that the quality is divided by 8 (virtually imperceptible to the human eye). The hidden image is encrypted on 3 bits (its quality is divided by 32 but it is the price to pay to hide it almost at best).

2- The code "desc1.java" allows to decrypt the image previously encrypted by "stegano1.java"

CONCLUSION :
the main drawback is that the encrypted and decrypted imqge are only available in png format

(Code already posted in https://codes-sources.commentcamarche.net/source/47570-steganographie few years ago)
