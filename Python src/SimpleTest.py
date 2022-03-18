import unittest

class SimpleTest(unittest.TestCase):
    def test(self):
        x= 20
        self.assertTrue((x == 10))

if __name__ == "__main__":
    unittest.main()
