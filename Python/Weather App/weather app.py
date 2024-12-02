from tkinter import *
import tkinter as tk
from geopy.geocoders import Nominatim
from tkinter import ttk, messagebox
from timezonefinder import TimezoneFinder
from datetime import datetime
import requests
import pytz

# Main Application Window
root = Tk()
root.title("Weather App")
root.geometry("900x500+300+200")
root.resizable(False, False)

# Function to Get Weather
def getWeather():
    city = textfield.get()
    
    geolocator = Nominatim(user_agent="my_custom_user_agent")
    location = geolocator.geocode(city)
    if not location:
        messagebox.showerror("Error", "City Not Found")
        return

    obj = TimezoneFinder()
    result = obj.timezone_at(lng=location.longitude, lat=location.latitude)
    
    home = pytz.timezone(result)
    local_time = datetime.now(home)
    current_time = local_time.strftime("%I:%M %p")
    clock.config(text=current_time)
    name.config(text="CURRENT WEATHER")

    # Weather API
    api = f"https://api.openweathermap.org/data/2.5/weather?q={city}&appid=aff2d710c439e2e443ddaca6697431eb"
    json_data = requests.get(api).json()
    
    if json_data.get('cod') != 200:
        messagebox.showerror("Error", "City Not Found")
        return

    condition = json_data['weather'][0]['main']
    description = json_data['weather'][0]['description']
    temp = int(json_data['main']['temp'] - 273.15)  # Convert from Kelvin to Celsius
    pressure = json_data['main']['pressure']
    humidity = json_data['main']['humidity']
    wind = json_data['wind']['speed']

    t.config(text=f"{temp}°C")
    c.config(text=f"{condition} | {description}")

    W.config(text=f"{wind} m/s")
    h.config(text=f"{humidity}%")
    d.config(text=f"{description}")
    p.config(text=f"{pressure} hPa")

# Background
bg_image = PhotoImage(file="sky.png")  # Replace with your image file
bg_label = Label(root, image=bg_image)
bg_label.place(relwidth=1, relheight=1)

# Search Box
textfield = tk.Entry(root, justify="center", width=17, font=("poppins", 25, "bold"), bg="#404040", border=0, fg="white")
textfield.place(x=50, y=40)
textfield.focus()

Search_icon = PhotoImage(file="search_icon.png")  # Replace with your search icon file
myimage_icon = Button(root, image=Search_icon, borderwidth=0, cursor="hand2", bg="#404040", command=getWeather)
myimage_icon.place(x=400, y=34)

# Logo
Logo_image = PhotoImage(file="logo.png")  # Replace with your logo file
logo = Label(root, image=Logo_image, bg="#2a5298")
logo.place(x=150, y=100)

# Bottom Box
frame_image = PhotoImage(file="box.png")  # Replace with your box image file
frame_myimage = Label(root, image=frame_image, bg="#1ab5ef")
frame_myimage.pack(padx=5, pady=2, side=BOTTOM)

# Time Display
name = Label(root, font=("arial", 15, "bold"), bg="#2a5298", fg="white")
name.place(x=30, y=100)
clock = Label(root, font=("Helvetica", 20), bg="#2a5298", fg="white")
clock.place(x=30, y=130)

# Weather Information Labels
label1 = Label(root, text="WIND", font=("Helvetica", 15, 'bold'), fg="white", bg="#1ab5ef")
label1.place(x=120, y=400)

label2 = Label(root, text="HUMIDITY", font=("Helvetica", 15, "bold"), fg="white", bg="#1ab5ef")
label2.place(x=225, y=400)

label3 = Label(root, text="DESCRIPTION", font=("Helvetica", 15, "bold"), fg="white", bg="#1ab5ef")
label3.place(x=430, y=400)

label4 = Label(root, text="PRESSURE", font=("Helvetica", 15, "bold"), fg="white", bg="#1ab5ef")
label4.place(x=650, y=400)

# Dynamic Weather Info
t = Label(root, font=("arial", 20, "bold"), fg="#ee666d", bg="#2a5298")
t.place(x=400, y=150)
c = Label(root, font=("arial", 15, "bold"), bg="#2a5298", fg="white")
c.place(x=400, y=250)

W = Label(root, text="....", font=("arial", 20, "bold"), bg="#1ab5ef")
W.place(x=120, y=430)
h = Label(root, text="....", font=("arial", 20, "bold"), bg="#1ab5ef")
h.place(x=280, y=430)
d = Label(root, text="....", font=("arial", 20, "bold"), bg="#1ab5ef")
d.place(x=450, y=430)
p = Label(root, text="....", font=("arial", 20, "bold"), bg="#1ab5ef")
p.place(x=670, y=430)

root.mainloop()
