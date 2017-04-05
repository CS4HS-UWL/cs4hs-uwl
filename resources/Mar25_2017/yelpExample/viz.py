import json   ## to read in JSON data
import pprint   ## to print dictionaries/lists for debugging
from bokeh.plotting import figure, show, output_file   ## to display/save the visualization
from bokeh.models import HoverTool   ## to produce tooltips

## load data
data = []
with open('./dataset/yelp_academic_dataset_business.json') as data_file:
	data = json.load(data_file)

## iterate over data; for each location, keep track of the total number of stars given and the total number of ratings
locDict = {}

for business in data:
	state = business['state']
	stars = business['stars']

	if state in locDict:
		locDict[state]['stars'] += stars
		locDict[state]['total'] += 1
	else:
		locDict[state] = {'stars': stars, 'total': 1}

## calculate axes/values
locations = list(locDict.keys())
avgRatings = []
for loc in locDict:
	avgRatings.append(locDict[loc]['stars']/locDict[loc]['total'])

## setup output file
output_file("viz.html")

## draw figure
TOOLS = "pan,wheel_zoom,box_zoom,reset,hover,save"
viz = figure(width=1200, height=600, x_range=locations, tools=TOOLS)
viz.vbar(x=locations, width=0.5, bottom=0, top=avgRatings, color="firebrick")

## define tooltip
hover = viz.select_one(HoverTool)
hover.point_policy = "follow_mouse"
hover.tooltips = [("Location", "$x"), ("Average Rating", "@top")]

## display visualization
show(viz)

## display data to console
pprint.pprint(locDict)
