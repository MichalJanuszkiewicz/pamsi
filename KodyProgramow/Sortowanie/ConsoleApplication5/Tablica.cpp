

#include "pch.h"
#include <iostream>
using namespace std;



//////////////////////////////////////////////////
////////   Michal Januszkiewicz 241572   /////////
//////////////////////////////////////////////////



/*
Zrodla algorytmow oraz inspiracje:

wikipedia.org/wiki/Sortowanie_introspektywne
wikipedia.org/wiki/Sortowanie_szybkie
wikipedia.org/wiki/Sortowanie_przez_scalanie
www.algorytm.edu.pl/
lukasz.jelen.staff.iiar.pwr.edu.pl/styled-2/page-2/index.php 
*/






template <typename Tablica>
void Stworz(Tablica tab[], int rozmiar, int procent)       //Tworzenie tablicy
{
	double wspolczynnik = 0.01 * procent;

	int iloscP = wspolczynnik * rozmiar;
	int iloscN = rozmiar - iloscP;

	int n = 0;
	for (int i = 0; i < iloscP; i++)
	{
		tab[i] = i;
		n++;
	}

	for (int j = 0; j < iloscN; j++)
	{
		tab[j+n] = rand() % 1001;
	}

}

template <typename Tablica>
void Wyswietl(Tablica tab[], int rozmiar)                   //Wyswietlanie tablicy 
{
	for (int i = 0; i < rozmiar; i++)
	{
		std::cout << tab[i];
		std::cout << " ";
	}
	std::cout << std::endl;
	std::cout << std::endl;

}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

template <typename Tablica>
void Quicksort(Tablica tab[], int lewy, int prawy)           //Algorytm quicksort
{


	if (prawy <= lewy) return;

	int i = lewy - 1, j = prawy + 1,
		pivot = tab[(lewy + prawy) / 2];
	while (1)
	{
		while (pivot > tab[++i]);

		while (pivot < tab[--j]);
		                                                                                                                           //////QUICKSORT//////
		if (i <= j)

			swap(tab[i], tab[j]);
		else
			break;
	}

	if (j > lewy)
		Quicksort(tab, lewy, j);
	if (i < prawy)
		Quicksort(tab, i, prawy);

}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

template <typename Tablica>
void merge(Tablica tab[], int start, int srodek, int koniec)   //Algorytm dzielenia tablicy
{
	int *tab_pom = new int[(koniec - start)];                  // utworzenie tablicy pomocniczej
	int i = start, j = srodek + 1, k = 0;                      // zmienne pomocnicze

	while (i <= srodek && j <= koniec)
	{
		if (tab[j] < tab[i])
		{
			tab_pom[k] = tab[j];
			j++;
		}
		else
		{
			tab_pom[k] = tab[i];
			i++;
		}
		k++;
	}

	if (i <= srodek)
	{
		while (i <= srodek)
		{                                                                                                                            //////MERGESORT//////
			tab_pom[k] = tab[i];
			i++;
			k++;
		}
	}
	else
	{
		while (j <= koniec)
		{
			tab_pom[k] = tab[j];
			j++;
			k++;
		}
	}

	for (i = 0; i <= koniec - start; i++)
		tab[start + i] = tab_pom[i];

}

template <typename Tablica>
void merge_sort(Tablica tab[], int start, int koniec)                  //sortowanie przez scalanie
{
	int srodek;

	if (start != koniec)
	{
		srodek = (start + koniec) / 2;
		merge_sort(tab, start, srodek);
		merge_sort(tab, srodek + 1, koniec);
		merge(tab, start, srodek, koniec);
	}
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



int choosePivot(int left, int right)                                       //wybor elementu osiowego
{
	div_t result = div((left + (right - 1)), 2);

	return result.quot;
}


template <typename Tablica>
Tablica* changeElements(Tablica* array, int index_1, int index_2)           //zamiana elementow tablicy
{
	Tablica tmp = array[index_1];
	array[index_1] = array[index_2];
	array[index_2] = tmp;

	return array;
}

template <typename Tablica>
int divideArray(Tablica* array, int left, int right)                        //podzial tablicy
{
	int centerElement = choosePivot(left, right);
	int Pivot = array[centerElement];

	changeElements(array, centerElement, right);

	int aktualPosition = left;

	for (int i = left; i <= right - 1; i++)
	{
		if (array[i] < Pivot)
		{
			changeElements(array, aktualPosition, i);
			aktualPosition++;
		}
	}

	changeElements(array, aktualPosition, right);

	return aktualPosition;
}

template <typename Tablica>
Tablica* insertion_sort(Tablica *array, int size)                             //sortowanie przez wstawianie
{
	for (int i = 1; i < size; i++)
	{
		Tablica tmp = array[i];

		while ((array[i - 1] > tmp) && ((i - 1) >= 0))
		{
			array[i] = array[i - 1];
			i--;
		}
		array[i] = tmp;
	}

	return array;
}
                                                                                                                        //////FUNKCJE POMOCNICZE DO INTROSPECTIVESORT//////


template <typename Tablica>
void checkMaxHeap(Tablica* array, int size, int parentIndex)
{
	int maxIndex = parentIndex;
	int leftIndex = parentIndex * 2 + 1;
	int rightIndex = parentIndex * 2 + 2;

	if (leftIndex<size && array[leftIndex]>array[maxIndex])
	{
		maxIndex = leftIndex;
	}
	if (rightIndex<size && array[rightIndex]>array[maxIndex])
	{
		maxIndex = rightIndex;
	}
	if (maxIndex != parentIndex)
	{
		Tablica tmp = array[maxIndex];
		array[maxIndex] = array[parentIndex];
		array[parentIndex] = tmp;
		checkMaxHeap(array, size, maxIndex);
	}
}


template <typename Tablica>
void heap_sort(Tablica* array, int size)                                   //sortowanie przez kopcowanie
{
	for (int i = (size / 2) - 1; i >= 0; i--)
	{
		checkMaxHeap(array, size, i);
	}

	for (int i = size - 1; i > 0; i--)
	{
		Tablica tmp = array[0];
		array[0] = array[i];
		array[i] = tmp;
		size--;
		checkMaxHeap(array, size, 0);
	}

}




template <typename Tablica>
void introSort(Tablica *array, int size, int M)                              //przygotowanie do sortowania
{
	int i;

	if (M <= 0)
	{
		heap_sort(array, size);
		return;
	}

	i = divideArray(array, 0, size - 1);
	if (i > 9)
		introSort(array, i, M - 1);
	if (size - 1 - i > 9)
		introSort(array + i + 1, size - 1 - i, M - 1);
}
                                                                                                                                            //////INTROSPECTIVESORT//////
template <typename Tablica>
void introspectiveSort(Tablica *array, int size)                               //sortowanie introspekcyjne
{
	introSort(array, size, (int)floor(2 * log(size) / 0.69314718055994530942));
	array = insertion_sort(array, size);
}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




template <typename Tablica>
void Zamiana(Tablica tab[], int rozmiar)            //zamiana elementow tablicy
{
	int i, tmp;

	for (i = 0; i < rozmiar/2; i++)
	{
		tmp = tab[i];
		tab[i] = tab[rozmiar - i];
		tab[rozmiar  - i] = tmp;
	}
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

int main()
{
	double suma = 0;
	double x = 0;



	for (int i = 0; i < 100; i++)
	{
		//int tablica[10000];
		int* tablica = new int[10000];
	
		Stworz(tablica, 10000, 50);
		//Wyswietl(tablica, 100);
		clock_t start = clock();

		Quicksort(tablica, 0, 9999);
		//merge_sort(tablica, 0, 499999);
		//introspectiveSort(tablica, 1000000);

		//Zamiana(tablica, 499999);
		//clock_t start = clock();
		

		x = clock() - start;
		suma += x;

		//Wyswietl(tablica, 100);
		delete[] tablica;
	}
	cout << "Czas wykonywania algorytmu:" << suma/1000 <<"s"<< endl;
	cout << "Sredni czas sortowania jednej tablicy:" << suma/100000 <<"s"<< endl;
}
