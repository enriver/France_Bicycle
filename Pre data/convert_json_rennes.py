# -*- coding: utf-8 -*-
"""
Created on Mon Mar 23 18:20:59 2020

@author: Stian
"""

import json

with open('rennes_bikes_records.json', 'r') as f:
    distros_dict = json.load(f)

for distro in distros_dict:
    distro['fields']['@type'] = "station"
    
        
    
with open('rennes_bikes_records.json', 'w') as fp:
   json.dump(distros_dict, fp)