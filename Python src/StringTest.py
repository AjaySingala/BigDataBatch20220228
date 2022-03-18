import unittest

class TestStringMethods(unittest.TestCase):

    def test_strings_a(self):
        self.assertEqual("a" * 4, "aaaa")

    def test_upper(self):
        self.assertEqual("foo".upper(), "FOO")

    def test_isupper(self):
        self.assertTrue("FOO".isupper())

    def test_split(self):
        msg = "Hello world"
        self.assertEqual(msg.split(), ["Hello", "World"])

if __name__ == "__main__":
    unittest.main()
