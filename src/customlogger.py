import logging

logging.basicConfig(filename = "app.log", filemode="w", \
    format="%(process)d - %(asctime)s - %(name)s - %(levelname)s - %(message)s", \
        datefmt = "%d-%b-%y %H:%M:%S")
logger = logging.getLogger("customlogger")
logger.warning("This is a warning")
