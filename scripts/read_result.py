import numpy as np
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error, r2_score
import matplotlib.pyplot as plt

def read_gravitational_data(filename):
    """Liest die Gravitationskraft aus der angegebenen Datei."""
    encodings = ['utf-8', 'iso-8859-1', 'windows-1252']
    for encoding in encodings:
        try:
            with open(filename, 'r', encoding=encoding) as file:
                data = file.read()
            
            # Extrahieren der Gravitationskraft aus dem String
            force_str = data.split('Gravitational Force: ')[1].strip()
            force_str = force_str.replace(',', '.')
            force = float(force_str)
            print(f"Erfolgreich gelesen mit Kodierung: {encoding}")
            return force
        except IndexError:
            print("Konnte die Gravitationskraft nicht finden.")
        except UnicodeDecodeError:
            print(f"Konnte nicht mit Kodierung {encoding} lesen")
        except Exception as e:
            print(f"Fehler beim Lesen der Datei: {e}")
    
    raise ValueError("Konnte die Datei mit keiner der versuchten Kodierungen lesen")

def generate_data(force, n_samples=1000):
    """Generiert zufällige Daten für Massen und Abstände und berechnet die Gravitationskraft."""
    m1 = np.random.uniform(1e24, 1e25, n_samples)
    m2 = np.random.uniform(1e22, 1e23, n_samples)
    r = np.random.uniform(3e8, 4e8, n_samples)
    
    G = 6.67430e-11  # Gravitationskonstante
    forces = G * (m1 * m2) / (r ** 2)
    forces = forces * (force / np.mean(forces))
    
    return np.column_stack((m1, m2, r)), forces

def train_model(X, y):
    """Trainiert ein lineares Regressionsmodell und bewertet es."""
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
    model = LinearRegression()
    model.fit(X_train, y_train)
    y_pred = model.predict(X_test)
    mse = mean_squared_error(y_test, y_pred)
    r2 = r2_score(y_test, y_pred)
    
    return model, mse, r2, X_test, y_test, y_pred

def plot_results(X_test, y_test, y_pred):
    """Plottet die tatsächlichen vs. vorhergesagten Gravitationskräfte."""
    plt.figure(figsize=(10, 6))
    plt.scatter(y_test, y_pred, color='blue', alpha=0.5)
    plt.plot([y_test.min(), y_test.max()], [y_test.min(), y_test.max()], 'r--', lw=2)
    plt.xlabel('Tatsächliche Gravitationskraft (N)')
    plt.ylabel('Vorhergesagte Gravitationskraft (N)')
    plt.title('Tatsächliche vs. Vorhergesagte Gravitationskraft')
    plt.savefig('gravity_prediction.png')
    plt.show()

if __name__ == "__main__":
    # Lesen der Gravitationskraft aus test.txt
    force = read_gravitational_data('test.txt')
    print(f"Gelesene Gravitationskraft: {force} N")
    
    # Generieren von Trainingsdaten
    X, y = generate_data(force)
    
    # Trainieren des Modells
    print("Trainiere das Modell...")
    model, mse, r2, X_test, y_test, y_pred = train_model(X, y)
    print("Modell erfolgreich trainiert.")
    
    print(f"Mean Squared Error: {mse}")
    print(f"R-squared Score: {r2}")
    
    # Plotten der Ergebnisse
    plot_results(X_test, y_test, y_pred)
    
    # Beispielvorhersage
    example_data = np.array([[5.972e24, 7.348e22, 3.844e8]])  # Erde-Mond-System
    prediction = model.predict(example_data)
    print(f"Vorhergesagte Gravitationskraft für das Erde-Mond-System: {prediction[0]:.2e} N")