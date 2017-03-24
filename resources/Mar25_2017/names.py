"""
Baby name application

See the Social Security Administration's Website for some visualizations and
tools for exploring the data:
https://www.ssa.gov/oact/babynames/

Download the data yourself:
https://www.ssa.gov/oact/babynames/limits.html
"""

import sys


def get_girl_data(the_data):
    # data looks like:
    #   <name>,F,#
    retval = []   # empty list to populate
    for line in the_data:
        # DEBUG: print the line to make sure we read it in correctly
        #print(line)
        # remove newline at the end of string
        line = line.strip()
        # split the string into 3 pieces by commas
        #    n : name
        #    g : gender ("F" or "M")
        #    p : number of people with that name
        n, g, p = line.split(",")

        # save the data if gender is "F"
        if g == "F":
            retval.append([n, int(p)])
    return retval

def get_boy_data(the_data):
    # data looks like:
    #   <name>,M,#
    retval = []   # empty list to populate
    for line in the_data:
        # DEBUG: print the line to make sure we read it in correctly
        #print(line)
        # remove newline at the end of string
        line = line.strip()
        # split the string into 3 pieces by commas
        #    n : name
        #    g : gender ("F" or "M")
        #    p : number of people with that name
        n, g, p = line.split(",")

        # save the data if gender is "M"
        if g == "M":
            # update adds the dictionary entry into the dictionary
            retval.append([n, int(p)])
    return retval


def find_name(data, name):
    """
    This function will search through the data list and find the index of the
    list containing name.  If name does not appear in list, return -1.
    """
    for i in range(len(data)):
        if data[i][0] == name:
            return i
    return -1


# program starts here
if __name__ == "__main__":
    # open a file and read the contents
    f = open("yob2014.txt", "r")
    data = f.readlines()
    # data contains a list of strings from the file.
    # Each line is a separate string.

    # parse the data into data about girls and boys
    # each dictionary holds the name as the key and the number of people with
    #    that name as the value at that key.
    girls = get_girl_data(data)
    boys = get_boy_data(data)

    # now we have data, we can play with it!

    print("The top 10 names for girls in 2014 are: ")
    for i in range(10):
        print("{0}) {1} ({2})".format(i, girls[i][0], girls[i][1]))

    print("The top 10 names for boys in 2014 are: ")
    for i in range(10):
        print("{0}) {1} ({2})".format(i, boys[i][0], boys[i][1]))


    # get name to search for
    name = input("Enter a name to look for in the data: ")
    # call function to do search
    index = find_name(girls, name)
    if index > 0:
        print ("There were {0} girls born in 2014 with the name {1}.".format(girls[index][1], name))
    else:
        print ("No girls were named {0} in 2014.".format(name))

    index = find_name(boys, name)
    if index > 0:
        print ("There were {0} boys born in 2014 with the name {1}.".format(boys[index][1], name))
    else:
        print ("No boys were named {0} in 2014.".format(name))


    # write your own functions to search through the data and display results
    # some ideas:
    #      - display all the names with more than X people with that name
    #      - display all the names with less than X people with that name
    #      - read in multiple files and show the change in popularity
    #      - show the names and popularity of names that start with XXX
