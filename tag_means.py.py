import json
import pandas as pd
#from tqdm import tqdm

filepath = 'test_data.json'

with open(filepath) as infile:
    o = json.load(infile)
    chunkSize = 1000
    for i in range(0, len(o), chunkSize):
        with open('file_' + str(i//chunkSize) + '.json', 'w') as outfile:
            json.dump(o[i:i+chunkSize], outfile)

def load(filepath):
    # using ijson read the file
    # using csv writer , writer.writerow(['column_name'1, 2, etc.])
    # for each entry in the json, create a row of values as a list
    # writer.writerow([list of values])
    # close writer
    # load using pandas.read_csv


data = load(filepath)

print(data.head())
print(data.describe())
    
df = pd.read_json('file_0.json')[['metric_sets_first_resolution_time_in_minutes_calendar',
                                  'tickets_tags', 'tickets_assignee_id', 'tickets_subject', 'tickets_group_id']]

import os
folder = 'data'
for file in os.listdir(folder):
    temp = pd.read_json('file_0.json')[['metric_sets_first_resolution_time_in_minutes_calendar',
                                  'tickets_tags', 'tickets_assignee_id', 'tickets_subject', 'tickets_group_id']]
    df = pd.concat([df,temp], ignore_index = True)
    

df.head()

#create a list of all the unique tags
tag_str = ''
for i in range(0, len(df)):
    tag_str = tag_str + df.loc[i]['tickets_tags']
temp = tag_str.split(' ')
tags = set(temp)
print(tags)    


import csv
with open('tags.csv', 'w') as f:
    writer = csv.writer(f)
    for tag in tags:
        writer.writerow([tag])
		
#tag: [resolution time, number of appearance]
tag_counts = {word:[0,0] for word in tags}

print(tag_counts)

#in each row of the df, tokenize the tags and add them to the tags list if they don't already exist, then increment each word's count
for i in range(0, len(df)):
    tag_list = df.loc[i]['tickets_tags'].split() #tokenize the tags column
    for word in tag_list:
        if word not in tag_counts:
            tag_counts[word] = [0,0]
        tag_counts[word][0] += df.loc[i]['metric_sets_first_resolution_time_in_minutes_calendar']
        tag_counts[word][1] += 1


#calculate mean resolution time and sort
print(tag_counts['i_need_to_edit_or_cancel_an_order'])	


for key, value in tag_counts.items():
    if counts[key][1] != 0:
        avg = tag_counts[key][0]/tag_counts[key][1]
        tag_counts[key].append(avg)
    else tag_counts[key].append(0) ####BUT EVERY WORD SHOULD HAVE A COUNT OF AT LEAST 1
	
	
	