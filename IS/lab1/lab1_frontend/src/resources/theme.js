import {createTheme} from '@mui/material/styles';

const theme = createTheme({
    palette: {
        mode: 'dark', // Установите режим на "темный"
        primary: {
            main: '#007ab6', // Цвет для основной палитры 007ab6
        },
        background: {
            default: '#282c34', // Цвет фона по умолчанию
        },
    },
    components: {
        MuiTextField: {
            styleOverrides: {
                root: {
                    backgroundColor: 'rgba(56,56,56,0.6)', // Серый фон с прозрачностью 60%
                    borderRadius: '4px', // Закругление углов
                    '& .MuiInputBase-root': {
                        backgroundColor: 'transparent', // Прозрачный фон для ввода
                    },
                },
            },
        },
    },
    tableContainer: {
        maxWidth: '100%',  // Ограничивает максимальную ширину контейнера
        overflowX: 'auto', // Добавляет прокрутку по горизонтали, если таблица слишком широка
        maxHeight: '60%',
        overflowY: 'auto',
    },
    table: {
        minWidth: '600px', // Минимальная ширина таблицы
    },
    cell: {
        whiteSpace: 'nowrap', // Запрет на перенос текста
    }
});

export default theme;