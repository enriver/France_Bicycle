# -*- coding: utf-8 -*-
"""
Created on Tue Mar 22 15:24:33 2020

@author: Stian
"""


import json

with open('lyon_bikes_records.json', 'r') as f:
    distros_dict = json.load(f)

for distro in distros_dict:
    distro['properties']['@type'] = "station"
    
with open('lyon_bikes_records.json', 'w') as fp:
   json.dump(distros_dict, fp)