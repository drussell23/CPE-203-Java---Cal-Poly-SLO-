class SimpleLoop
{
    public static int sum(int low, int high)
    {
        int finalValue = 0;

        for(int value = low; value <= high; value++)
        {
            finalValue += value;
        }
        return finalValue;
    }
}
