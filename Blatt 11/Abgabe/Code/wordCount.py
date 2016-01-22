#!/usr/bin/env python
# coding: utf8

str = '' # Text der Seite hier einfügen
count = 0
for c in str:
    o = ord(c)
    if (o >= 97 and o <= 122) or (o >= 65 and o <= 90):
        count += 1
print count