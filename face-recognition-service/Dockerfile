## 1️⃣ Use Python base image for building
#FROM python:3.9-slim AS build
#
## 2️⃣ Set the working directory
#WORKDIR /app
#
## 3️⃣ Install system dependencies (including CMake and g++)
#RUN apt-get update && \
#    apt-get install -y cmake g++ make && \
#    apt-get clean && \
#    rm -rf /var/lib/apt/lists/*
#
## 4️⃣ Copy requirements.txt into the container
#COPY requirements.txt .
#
## 5️⃣ Install Python dependencies
#RUN pip install --no-cache-dir -r requirements.txt
#
## 6️⃣ Copy the rest of your app
#COPY . .
#
## 7️⃣ Expose the port the Flask app will run on
#EXPOSE 5001
#
#COPY wait-for-it.sh .
#RUN chmod +x wait-for-it.sh
#
## 8️⃣ Run the app
#ENTRYPOINT ["python", "main.py"]


# 1️⃣ Use Python base image for building
FROM python:3.9-slim AS build

# 2️⃣ Set the working directory
WORKDIR /app

RUN apt-get update && \
    apt-get install -y cmake g++ make wget netcat-openbsd && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

## 3️⃣ Install system dependencies (including CMake and g++, also curl/netcat for wait-for-it.sh)
#RUN apt-get update && \
#    apt-get install -y cmake g++ make wget netcat && \
#    apt-get clean && \
#    rm -rf /var/lib/apt/lists/*

# 4️⃣ Copy requirements.txt into the container
COPY requirements.txt .

# 5️⃣ Install Python dependencies
RUN pip install --no-cache-dir -r requirements.txt

# 6️⃣ Copy the rest of your app
COPY . .

# 7️⃣ Download wait-for-it.sh
RUN wget -O /usr/local/bin/wait-for-it.sh https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh && \
    chmod +x /usr/local/bin/wait-for-it.sh

# 8️⃣ Expose the port the Flask app will run on
EXPOSE 5001

# 9️⃣ Use wait-for-it.sh to wait on dependency (e.g. server-registry)
ENTRYPOINT ["/usr/local/bin/wait-for-it.sh", "server-registry:8761", "--timeout=60", "--", "python", "main.py"]

