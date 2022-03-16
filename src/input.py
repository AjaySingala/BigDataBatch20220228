# print("Enter your name: ")
# name = input()
# print("You entered: ", name)

# prompt = "Enter a number: "
# num = int(input(prompt))
# print("You entered the number: ", num)

# This is a single line comment.

""" Here is an example of a multi-line comment.
Enter any number of lines between the quotes."""

'''
Here is an example of a multi-line comment.
Enter any number of lines between the single quotes.
'''

def add(x,y):
    """
    This function is used to add 2 numbers.
    It takes 2 numbers as parameters
    and returns the sum of both.
    """
    z = x + y
    return z

print(add.__doc__)
a = add(10,30)
print(a)
