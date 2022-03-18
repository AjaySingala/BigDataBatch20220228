x = set(['foo', 'bar', 'foobar', 'bar', 'quad', 'foo'])
print(x)

x = set(('foo', 'bar', 'foobar', 'bar', 'quad', 'foo'))
print(x)

q = 'quad'
q_list = list(q)
q_set = set(q)

q = 'quadu'
q_list = list(q)
q_set = set(q)

print(q)
print(q_list)
print(q_set)

x = {'foo', 'bar', 'foobar', 'bar', 'quad', 'foo'}
print(x)
print(type(x))

print(len(x))
print("foo" in x)
print("food" in x)

# union.
x = {'foo', 'bar', 'foobar', 'bar'}
y = {'food',  'bar', 'quad', 'foo'}
#z = x | y
z = x.union(y)
print(x)
print(y)
print(z)

# intersection.
x = {'foo', 'bar', 'foobar', 'bar'}
y = {'food',  'bar', 'quad', 'foo'}
#z = x & y
z = x.intersection(y)
print(x)
print(y)
print(z)


# difference.
x = {'foo', 'bar', 'foobar', 'bar'}
y = {'food',  'bar', 'quad', 'foo'}
#z = x - y
z = x.difference(y)
print(x)
print(y)
print(z)


print("Add values to a set...")
print(x)
x.add("baz")
print(x)

print("remove  values from a set...")
x.remove('bar')
print(x)

# remove item, if not found, exception.
# x.remove("anon")
# print(x)

# remove item, if not found, do nothing.
x.discard("anon")
print(x)

# remove an arbitrary item.
x.pop()
print(x)

# remove all items.
x.clear()
print(x)