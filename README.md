# Crib Dragging Cryptanalysis Tool

This repository contains a Java implementation of a **Crib Dragger**, an automated cryptanalysis tool used to exploit vulnerabilities stemming from stream cipher key reuse—commonly referred to as a **Many-Time Pad** vulnerability. 

When the same keystream is mistakenly used to encrypt two different plaintexts, an attacker can intercept the ciphertexts and eliminate the key entirely by XORing them together:

$$C_1 \oplus C_2 = (P_1 \oplus K) \oplus (P_2 \oplus K) = P_1 \oplus P_2$$

By isolating $P_1 \oplus P_2$, this tool recovers the plaintexts without ever knowing the secret key.

### How the Pipeline Works

1. **Hex to Byte Conversion:** Converts hexadecimal ciphertext strings into raw byte arrays for bitwise manipulation.
2. **Ciphertext XORing:** Computes the delta buffer ($C_1 \oplus C_2$) to cleanly strip out the reused keystream.
3. **Automated Crib Sliding:** Loads a dictionary file (`wordlist.txt`) containing likely words or phrases ("cribs"). It slides each crib byte-by-byte across the XORed matrix.
4. **Heuristic Plaintext Guessing:** Evaluates the resulting operations ($C_1 \oplus C_2 \oplus \text{crib}$). If the output yields readable ASCII printable text, it flags the indices as a valid collision match and prints out the recovered plaintext fragments for both messages.

### Cryptographic Takeaway
This project demonstrates why stateful initialization vectors or nonces are absolutely critical in stream ciphers like AES-CTR, ChaCha20, or One-Time Pads. Key reuse completely destroys confidentiality.
