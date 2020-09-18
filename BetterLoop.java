class BetterLoop
{
    public static boolean contains(int [] values, int v)
    {
        for(int each : values)
        {
            if (each == v)
            {
                return true;
            }
        }
        return false; // A bit optimistic, but a real boolean value.
    }
}