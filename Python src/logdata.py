from decimal import DivisionByZero
import logging

# logging.debug("This is a debug message")
# logging.info("This is a info message")
# logging.warning("This is a warning message")
# logging.error("This is a error message")
# logging.critical("This is a critical message")

# # Change the log level to DEBUG.
# logging.basicConfig(level = logging.DEBUG)
# logging.debug("This is a debug message 2")
# logging.info("This is a info message 2")
# logging.warning("This is a warning message 2")
# logging.error("This is a error message 2")
# logging.critical("This is a critical message 2")

# # Log to a file.
# logging.basicConfig(level=logging.DEBUG, filename = "app.log", filemode="w")
# logging.debug("This is a debug message")
# logging.info("This is a info message")
# logging.warning("This is a warning message")
# logging.error("This is a error message")
# logging.critical("This is a critical message")

# Log to a file.
logging.basicConfig(filename = "app.log", filemode="w", \
    format="%(process)d - %(asctime)s - %(name)s - %(levelname)s - %(message)s", \
        datefmt = "%d-%b-%y %H:%M:%S")
logging.debug("This is a debug message")
logging.info("This is a info message")
logging.warning("This is a warning message")
logging.error("This is a error message")
logging.critical("This is a critical message")

def divide(x,y):
    logging.debug("Entering divide()...")
    z = x / y
    logging.debug("Exiting divide()...")

    return z

a = 10
b = 0
try:
    print(divide(a,b))
except ZeroDivisionError as dbz_ex:
    logging.error(f"Attempt to divide by zero. Data provided: {a} and {b}", exc_info=True)
